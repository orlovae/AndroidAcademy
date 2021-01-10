package ru.aleksandrorlove.appname

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.aleksandrorlove.appname.data.Movie

class ViewModelMoviesList : ViewModel() {
    private var scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val repository: Repository = Repository.instance

    private lateinit var movies: List<Movie>

    var liveDataListMovie = MutableLiveData<List<Movie>>()

    fun init() {
        scope.launch {
            movies = repository.getListMovies()
            liveDataListMovie.value = movies
        }
    }
}