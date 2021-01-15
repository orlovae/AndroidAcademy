package ru.aleksandrorlove.appname

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.aleksandrorlove.appname.data.Movie
import ru.aleksandrorlove.appname.network.MoviePopular

class ViewModelMoviesList : ViewModel() {
    private var scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val repository: Repository = Repository.Singleton.instance

    private lateinit var movies: List<Movie>

    var liveDataListMovie = MutableLiveData<List<Movie>>()

    fun init() {
        scope.launch {
            val moviesPopular: List<MoviePopular>? = repository.getMoviesPopular()
            if (moviesPopular != null) {
                for (item in moviesPopular) {
                    Log.d("ViewModelMoviesList", " movie popular = " + item.toString())
                }
            } else {
                Log.d("ViewModelMoviesList", " movie popular is null ")
            }
            movies = repository.getListMovies()
            liveDataListMovie.value = movies
        }
    }
}