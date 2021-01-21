package ru.aleksandrorlove.appname

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.aleksandrorlove.appname.Entity.MovieEntity

class ViewModelMovieDetails : ViewModel() {
    private val mapper: Mapper = Mapper()

    var liveDataMovie = MutableLiveData<MovieEntity>()

    fun onPressItemRecyclerView(id: Int) {
        liveDataMovie.value = mapper.getMovieEntity(id)
    }
}