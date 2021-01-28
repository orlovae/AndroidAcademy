package ru.aleksandrorlove.appname

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.aleksandrorlove.appname.model.Movie
import ru.aleksandrorlove.appname.network.ManagerNetwork

class ViewModelMovieDetails : ViewModel() {
    private val managerNetwork: ManagerNetwork = ManagerNetwork()

    var liveDataMovie = MutableLiveData<Movie>()

    fun onPressItemRecyclerView(id: Int) {
        liveDataMovie.value = managerNetwork.getMovieFromNetwork(id)
    }
}