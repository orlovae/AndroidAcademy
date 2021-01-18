package ru.aleksandrorlove.appname

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.annotations.Since
import ru.aleksandrorlove.appname.Entity.MovieEntity
import ru.aleksandrorlove.appname.data.Movie

class ViewModelMovieDetails : ViewModel() {
    private val repository: Repository = Repository.Singleton.instance

    private lateinit var movieEntity: MovieEntity

    var liveDataMovie = MutableLiveData<MovieEntity>()


    fun onPressItemRecyclerView(id: Int) {
        movieEntity = repository.getMovieEntity(id)
        liveDataMovie.value = movieEntity
    }
}