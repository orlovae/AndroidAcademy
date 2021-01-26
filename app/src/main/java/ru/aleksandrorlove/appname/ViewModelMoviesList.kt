package ru.aleksandrorlove.appname

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.aleksandrorlove.appname.model.Movie
import ru.aleksandrorlove.appname.network.MapperNetwork
import ru.aleksandrorlove.appname.storage.DbRepository
import ru.aleksandrorlove.appname.storage.MapperDb

class ViewModelMoviesList() : ViewModel() {
    private var scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val mapperNetwork: MapperNetwork = MapperNetwork()
    private val mapperDb: MapperDb = MapperDb()

    var liveDataListMovie = MutableLiveData<List<Movie>>()

    private val moviesMutableData = MutableLiveData<List<Movie>>()
    private val errorMessageMutableData = MutableLiveData<String>()

    val movies: LiveData<List<Movie>> = moviesMutableData
    val errorMessage: LiveData<String> = errorMessageMutableData

    fun loadMovies() {

    }


    fun init() {

        val dbRepository: DbRepository = DbRepository()
        scope.launch {
            liveDataListMovie.value = mapperDb.mapFromDbToModel(dbRepository.readMoviesFromDb())
            liveDataListMovie.value = mapperNetwork.mapListMoviePopularNetworkToListMoviesEntity()
        }
    }
}