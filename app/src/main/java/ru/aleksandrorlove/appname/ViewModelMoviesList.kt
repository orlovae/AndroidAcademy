package ru.aleksandrorlove.appname

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.aleksandrorlove.appname.model.Movie
import ru.aleksandrorlove.appname.network.ManagerNetwork
import ru.aleksandrorlove.appname.network.Result
import ru.aleksandrorlove.appname.storage.MapperDb
import ru.aleksandrorlove.appname.storage.RepositoryDb
import ru.aleksandrorlove.appname.workmanager.UploadWorker
import java.util.concurrent.TimeUnit

class ViewModelMoviesList : ViewModel() {
    private val managerNetwork: ManagerNetwork = ManagerNetwork()
    private val mapperDb: MapperDb = MapperDb()

    private val moviesMutableData = MutableLiveData<List<Movie>>()
    private val errorMessageMutableData = MutableLiveData<String>()

    val movies: LiveData<List<Movie>> = moviesMutableData
    val errorMessage: LiveData<String> = errorMessageMutableData

    init {
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val uploadWorkRequest = PeriodicWorkRequestBuilder<UploadWorker>(
            15,
            TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        val workManager = WorkManager.getInstance(App.appContext)
        workManager.enqueueUniquePeriodicWork(
            "",
            ExistingPeriodicWorkPolicy.REPLACE,
            uploadWorkRequest
        )
    }

    fun loadMovies() {
        val repositoryDb: RepositoryDb = RepositoryDb()
        viewModelScope.launch {
            val localMovies = withContext(Dispatchers.IO) {
                mapperDb.mapListFromDbToModel(repositoryDb.readMoviesFromDb())
            }

            if (localMovies.isNotEmpty()) {
                moviesMutableData.value = localMovies
            }

            val remoteMoviesResult = withContext(Dispatchers.IO) {
                managerNetwork.getResultListMovieFromNetwork()
            }

            if (remoteMoviesResult is Result.Success) {
                val newMovies: List<Movie> = remoteMoviesResult.data as List<Movie>

                withContext(Dispatchers.IO) {
                    repositoryDb.deleteAllToDb()
                    repositoryDb.saveListMovieToDb(mapperDb.mapListFromModelToDb(newMovies))
                }
                moviesMutableData.value = newMovies
            } else if (remoteMoviesResult is Result.Error) {
                errorMessageMutableData.value = remoteMoviesResult.message
            }
        }
    }
}