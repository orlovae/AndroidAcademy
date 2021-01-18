package ru.aleksandrorlove.appname

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.aleksandrorlove.appname.Entity.MovieEntity

class ViewModelMoviesList : ViewModel() {
    private var scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val repository: Repository = Repository.Singleton.instance

    private lateinit var moviesEntity: List<MovieEntity>

    var liveDataListMovieEntity = MutableLiveData<List<MovieEntity>>()

    fun init() {
        scope.launch {
            repository.init()

            liveDataListMovieEntity.value = repository.moviesEntity
        }
    }
}