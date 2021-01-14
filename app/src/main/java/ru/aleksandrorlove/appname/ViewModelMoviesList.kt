package ru.aleksandrorlove.appname

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.Response
import ru.aleksandrorlove.appname.data.Movie
import ru.aleksandrorlove.appname.network.MoviePopular
import ru.aleksandrorlove.appname.network.ResponseMoviePopular

class ViewModelMoviesList : ViewModel() {
    private var scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val repository: Repository = Repository.Singleton.instance

    private lateinit var movies: List<Movie>

    var liveDataListMovie = MutableLiveData<List<Movie>>()

    fun init() {
        scope.launch {
            movies = repository.getListMovies()
            liveDataListMovie.value = movies
        }
    }
}