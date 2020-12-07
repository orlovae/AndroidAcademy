package ru.aleksandrorlove.appname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentMoviesList : Fragment(), CellClickListener {
    lateinit var movies: ArrayList<Movie>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_movies_list, container, false)
        val recyclerViewMovies: RecyclerView = view.findViewById(R.id.recyclerview_movies)
        movies = Movies.createMovieList() as ArrayList<Movie>
        val adapter = MoviesAdapter(movies, this)
        recyclerViewMovies.adapter = adapter
        recyclerViewMovies.apply { layoutManager = GridLayoutManager(requireContext(), 2) }

        return view
    }

    override fun onCellClickListener() {
        Toast.makeText(requireContext(), "Card Clicked", Toast.LENGTH_SHORT).show()
    }

//    override fun onClick(v: View?) {
//        when (v?.id) {
//            R.id.card_view -> {
//                val fragmentMoviesDetails = FragmentMoviesDetails()
//                val tag = fragmentMoviesDetails.tag
//
//                val fragmentManager = requireActivity().supportFragmentManager
//                val fragmentTransition = fragmentManager.beginTransaction()
//
//                fragmentTransition.replace(R.id.fragment_container_view, fragmentMoviesDetails, tag)
//                fragmentTransition.addToBackStack(tag)
//                fragmentTransition.commit()
//            }
//        }
//    }
}