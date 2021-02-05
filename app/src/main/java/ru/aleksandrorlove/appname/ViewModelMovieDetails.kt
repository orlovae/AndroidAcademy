package ru.aleksandrorlove.appname

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.aleksandrorlove.appname.model.Movie
import ru.aleksandrorlove.appname.network.ManagerNetwork
import ru.aleksandrorlove.appname.network.Result
import ru.aleksandrorlove.appname.storage.MapperDb
import ru.aleksandrorlove.appname.storage.RepositoryDb

class ViewModelMovieDetails : ViewModel() {
    private val managerNetwork: ManagerNetwork = ManagerNetwork()
    private val mapperDb: MapperDb = MapperDb()

    private val movieMutableData = MutableLiveData<Movie>()
    private val errorMessageMutableData = MutableLiveData<String>()

    val movie: LiveData<Movie> = movieMutableData
    val errorMessage: LiveData<String> = errorMessageMutableData

    fun onPressItemRecyclerView(id: Int) {
        val repositoryDb: RepositoryDb = RepositoryDb()

        viewModelScope.launch {
            val localMovie = withContext(Dispatchers.IO) {
                mapperDb.mapMovieFromDbToModel(repositoryDb.readMovieFromDb(id))
            }

            if (localMovie.id != 0) {
                movieMutableData.value = localMovie
            }

            val remoteMovieResult = withContext(Dispatchers.IO) {
                managerNetwork.getResultMovieDetailsNetwork(id)
            }

            if (remoteMovieResult is Result.Success) {
                val newMovie: Movie = remoteMovieResult.data as Movie

                withContext(Dispatchers.IO) {
                    repositoryDb.updateMovieToDb(mapperDb.mapMovieFromModelToDb(newMovie))
                }

                movieMutableData.value = newMovie
            } else if (remoteMovieResult is Result.Error) {
                errorMessageMutableData.value = remoteMovieResult.message
            }
        }
    }
}