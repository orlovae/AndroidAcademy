package ru.aleksandrorlove.appname

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.aleksandrorlove.appname.model.Movie
import ru.aleksandrorlove.appname.network.MapperNetwork
import ru.aleksandrorlove.appname.storage.DbRepository

class ViewModelMoviesList : ViewModel() {
    private var scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val mapperNetwork: MapperNetwork = MapperNetwork()

    var liveDataListMovieEntity = MutableLiveData<List<Movie>>()

    fun init() {

        val dbRepository: DbRepository = DbRepository()
        scope.launch {
            dbRepository.readMoviesFromDb()
            liveDataListMovieEntity.value = mapperNetwork.mapListMoviePopularNetworkToListMoviesEntity()
        }
    }
}