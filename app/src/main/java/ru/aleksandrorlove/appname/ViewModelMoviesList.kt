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
import ru.aleksandrorlove.appname.notification.NotificationManager
import ru.aleksandrorlove.appname.storage.MapperDb
import ru.aleksandrorlove.appname.storage.RepositoryDb

class ViewModelMoviesList : ViewModel() {
    private val managerNetwork: ManagerNetwork = ManagerNetwork()
    private val mapperDb: MapperDb = MapperDb()

    private val notificationManager = NotificationManager.Singleton.instance

    private val moviesMutableData = MutableLiveData<List<Movie>>()
    private val errorMessageMutableData = MutableLiveData<String>()

    val movies: LiveData<List<Movie>> = moviesMutableData
    val errorMessage: LiveData<String> = errorMessageMutableData

    init {
//        val constraints = Constraints.Builder()
//            .setRequiresCharging(true)
//            .setRequiredNetworkType(NetworkType.UNMETERED)
//            .build()
//
//        val uploadWorkRequest = PeriodicWorkRequest.Builder(
//            UploadWorker::class.java, 15, TimeUnit.MINUTES
//        )
//            .addTag("UploadWorker")
//            .setConstraints(constraints)
//            .build()
//
//        WorkManager.getInstance(App.appContext).enqueue(uploadWorkRequest)
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
                val remoteMovies: List<Movie> = remoteMoviesResult.data as List<Movie>

                initNotification(localMovies, remoteMovies)

                withContext(Dispatchers.IO) {
                    repositoryDb.deleteAllToDb()
                    repositoryDb.saveListMovieToDb(mapperDb.mapListFromModelToDb(remoteMovies))
                }
                moviesMutableData.value = remoteMovies
            } else if (remoteMoviesResult is Result.Error) {
                errorMessageMutableData.value = remoteMoviesResult.message
            }
        }
    }

    private fun initNotification(moviesFromDb: List<Movie>, moviesFromNetwork: List<Movie>) {
        val newMovies: List<Movie> = findNewMovies(moviesFromDb, moviesFromNetwork)

        val movieForNotification: Movie = if (newMovies.isEmpty()) {
            findMovieHighRating(moviesFromDb)
        } else {
            findMovieHighRating(newMovies)
        }
        
        val notification = notificationManager.createNotification(movieForNotification)
        
        notificationManager.showNotification(notification)
    }

    private fun findNewMovies(moviesFromDb: List<Movie>, moviesFromNetwork: List<Movie>) : List<Movie> {
        val findMovies = mutableListOf<Movie>()

        moviesFromNetwork.forEach {
            if (!moviesFromDb.contains(it)) {
                findMovies.add(it)
            }
        }
        return findMovies.toList()
    }

    private fun findMovieHighRating(movies: List<Movie>) : Movie {
        var findMovie = movies[0]
        var ratingHigh = findMovie.ratings
        for (movie in movies) {
            if (ratingHigh < movie.ratings) {
                ratingHigh = movie.ratings
                findMovie = movie
            }
        }
        return findMovie
    }
}