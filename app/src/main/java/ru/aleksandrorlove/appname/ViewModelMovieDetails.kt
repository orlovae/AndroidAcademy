package ru.aleksandrorlove.appname

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.aleksandrorlove.appname.model.Movie
import ru.aleksandrorlove.appname.network.ManagerNetwork
import ru.aleksandrorlove.appname.network.ManagerNetwork2

class ViewModelMovieDetails : ViewModel() {
    private val managerNetwork2: ManagerNetwork2 = ManagerNetwork2()

    var liveDataMovie = MutableLiveData<Movie>()

    fun onPressItemRecyclerView(id: Int) {
        liveDataMovie.value = managerNetwork2.getMovieFromNetwork(id)
    }
}