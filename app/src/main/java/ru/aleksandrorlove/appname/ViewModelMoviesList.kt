package ru.aleksandrorlove.appname

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.aleksandrorlove.appname.model.Movie
import ru.aleksandrorlove.appname.network.ManagerNetwork2
import ru.aleksandrorlove.appname.network.Result
import ru.aleksandrorlove.appname.storage.MapperDb
import ru.aleksandrorlove.appname.storage.RepositoryDb

class ViewModelMoviesList : ViewModel() {
    private val managerNetwork2: ManagerNetwork2 = ManagerNetwork2()
    private val mapperDb: MapperDb = MapperDb()

    private val moviesMutableData = MutableLiveData<List<Movie>>()
    private val errorMessageMutableData = MutableLiveData<String>()

    val movies: LiveData<List<Movie>> = moviesMutableData
    val errorMessage: LiveData<String> = errorMessageMutableData

    fun loadMovies() {
        val repositoryDb: RepositoryDb = RepositoryDb()
        viewModelScope.launch {
            val localMovies = withContext(Dispatchers.IO) {
                mapperDb.mapFromDbToModel(repositoryDb.readMoviesFromDb())
            }

            if (localMovies.isNotEmpty()) {
                moviesMutableData.value = localMovies
            }

            val remoteMoviesResult = withContext(Dispatchers.IO) {
                managerNetwork2.getResultListMovieFromNetwork()
            }

            if (remoteMoviesResult is Result.Success) {
                val newMovies = remoteMoviesResult.data

                withContext(Dispatchers.IO) {
                    repositoryDb.deleteAllToDb()
                    repositoryDb.saveMovieToDb(mapperDb.mapFromModelToDb(newMovies as List<Movie>))
                }
                moviesMutableData.value = newMovies as List<Movie>
            } else if (remoteMoviesResult is Result.Error) {
                errorMessageMutableData.value = remoteMoviesResult.message
            }
        }
    }
}