package ru.aleksandrorlove.appname

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.aleksandrorlove.appname.model.Movie
import ru.aleksandrorlove.appname.network.MapperNetwork

class ViewModelMovieDetails : ViewModel() {
    private val mapperNetwork: MapperNetwork = MapperNetwork()

    var liveDataMovie = MutableLiveData<Movie>()

    fun onPressItemRecyclerView(id: Int) {
        liveDataMovie.value = mapperNetwork.getMovieEntity(id)
    }
}