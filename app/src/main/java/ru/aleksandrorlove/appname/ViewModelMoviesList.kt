package ru.aleksandrorlove.appname

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.aleksandrorlove.appname.Entity.MovieEntity
import ru.aleksandrorlove.appname.data.Movie
import ru.aleksandrorlove.appname.network.*

class ViewModelMoviesList : ViewModel() {
    private var scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val repository: Repository = Repository.Singleton.instance

    private lateinit var movies: List<Movie>

    var liveDataListMovie = MutableLiveData<List<Movie>>()

    fun init() {
        scope.launch {
            val images: Images? = repository.getConfiguration()
//            if (images != null) {
//                Log.d("ViewModelMoviesList", " image = " + images.toString())
//            } else {
//                Log.d("ViewModelMoviesList", " genre is null ")
//            }



            val moviesPopular: List<MoviePopular>? = repository.getMoviesPopularOrEmptyList()
            if (moviesPopular != null) {
                for (item in moviesPopular) {
                    Log.d("ViewModelMoviesList", " movie popular = " + item.toString())
                }
            } else {
                Log.d("ViewModelMoviesList", " movie popular is null ")
            }

            val genres: List<Genre>? = repository.getGenresOrEmptyList()
            if (genres != null) {
                for (item in genres) {
                    Log.d("ViewModelMoviesList", " genre = " + item.toString())
                }
            } else {
                Log.d("ViewModelMoviesList", " genre is null ")
            }

//            val actors: List<Actor>? = repository.getActorsOrEmptyList(671039)
//            if (actors != null) {
//                for (item in actors) {
//                    Log.d("ViewModelMoviesList", " actor = " + item.toString())
//                }
//            } else {
//                Log.d("ViewModelMoviesList", " actor is null ")
//            }

            if (genres != null && moviesPopular != null) {
                val moviesEntity: List<MovieEntity> = repository.getMoviesEntity(genres, moviesPopular)
                for (item in moviesEntity) {
                    Log.d("ViewModelMoviesList", " moviesEntity = " + item.toString())
                }
            }




            movies = repository.getListMovies()
            liveDataListMovie.value = movies
        }
    }
}