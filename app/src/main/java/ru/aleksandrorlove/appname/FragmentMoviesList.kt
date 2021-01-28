package ru.aleksandrorlove.appname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.aleksandrorlove.appname.model.Movie

class FragmentMoviesList : Fragment(), CellClickListener {
    private var adapter = AdapterMovies(arrayListOf<Movie>(), this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vm: ViewModelMoviesList = ViewModelProvider(this).get(ViewModelMoviesList::class.java)
        vm.init()
        vm.liveDataListMovie.observe(
            viewLifecycleOwner,
            Observer<List<Movie>> {
                it?.let {
                    adapter.movies =
                        vm.liveDataListMovie.value as ArrayList<Movie>
                    adapter.notifyDataSetChanged()
                }
            })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_movies_list, container, false)
        val recyclerViewMovies: RecyclerView = view.findViewById(R.id.recyclerview_movies)
        recyclerViewMovies.adapter = adapter
        recyclerViewMovies.apply { layoutManager = GridLayoutManager(requireContext(), 2) }

        return view
    }

    override fun onCellClickListener(id: Int) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()

        val fragmentMoviesDetails = FragmentMoviesDetails.newInstance(id)
        fragmentTransition.replace(
            R.id.fragment_container_view,
            fragmentMoviesDetails,
            "fragmentMoviesDetails"
        )
        fragmentTransition.addToBackStack(tag)
        fragmentTransition.commit()
    }
}