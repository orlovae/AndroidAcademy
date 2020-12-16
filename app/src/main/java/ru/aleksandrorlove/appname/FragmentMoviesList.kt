package ru.aleksandrorlove.appname

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import ru.aleksandrorlove.appname.data.Movie
import ru.aleksandrorlove.appname.data.loadMovies
import kotlin.coroutines.suspendCoroutine

class FragmentMoviesList : Fragment(), CellClickListener {
    lateinit var movies: ArrayList<Movie>
    private var scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_movies_list, container, false)
        val recyclerViewMovies: RecyclerView = view.findViewById(R.id.recyclerview_movies)
//        movies = Movies.createMovieList() as ArrayList<Movie>

        scope.launch {
            load()
        }

        val adapter = MoviesAdapter(movies, this)
        recyclerViewMovies.adapter = adapter
        recyclerViewMovies.apply { layoutManager = GridLayoutManager(requireContext(), 2) }

        return view
    }

    override fun onCellClickListener(movie: Movie) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()
//        val fragmentMoviesDetails = FragmentMoviesDetails.newInstance(movie)
//        fragmentTransition.replace(
//            R.id.fragment_container_view,
//            fragmentMoviesDetails,
//            "fragmentMoviesDetails"
//        )
//        fragmentTransition.addToBackStack(tag)
//        fragmentTransition.commit()
    }

    private suspend fun load() {
        movies = loadMovies(requireContext()) as ArrayList<Movie>
    }
}