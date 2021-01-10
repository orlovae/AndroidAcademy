package ru.aleksandrorlove.appname

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.aleksandrorlove.appname.data.Movie

class ViewModelMovieDetails : ViewModel() {
    private val repository: Repository = Repository.instance

    private lateinit var movie: Movie

    var liveDataMovie = MutableLiveData<Movie>()


    fun onPressItemRecyclerView(id: Int) {
        movie = repository.getMovie(id)
        liveDataMovie.value = movie
    }
}