package ru.aleksandrorlove.appname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import ru.aleksandrorlove.appname.databinding.FragmentMoviesDetailsBinding
import ru.aleksandrorlove.appname.model.Genre
import ru.aleksandrorlove.appname.model.Movie

class FragmentMoviesDetails : Fragment(), View.OnClickListener {
    private var id: Int? = null
    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { id = it.getInt(ARG_MOVIE_ID) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: ViewModelMovieDetails =
            ViewModelProvider(this).get(ViewModelMovieDetails::class.java)

        viewModel.movie.observe(viewLifecycleOwner,
            { movie -> showMovie(movie) })

        viewModel.errorMessage.observe(viewLifecycleOwner,
            {message -> showToast(message)})

        if (savedInstanceState == null) {
            id?.let { viewModel.onPressItemRecyclerView(it) }
        }

    }

    private fun showToast(message: String) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        val view: ScrollView = binding.root

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.movie_details_button_back -> {
                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                fragmentManager.popBackStack()
            }
        }
    }

    private fun showMovie(movie: Movie) {
        binding.movieDetailsButtonBack.setOnClickListener(this)
        binding.movieDetailsBackgroundTop.setImageResource(R.drawable.background_gradient)
        Glide.with(requireActivity())
            .load(movie.backdrop)
            .into(binding.movieDetailsBackgroundTop)

        binding.movieDetailsTextViewMinimumAge.text = movie.minimumAge.toString()

        binding.movieDetailsTextViewTitle.text = movie.title

        val genres: String = getGenresName(movie.genres)
        binding.movieDetailsTextViewGenre.text = genres

        val starImageViewList: List<ImageView> = listOf(
            binding.movieDetailsStar01,
            binding.movieDetailsStar02,
            binding.movieDetailsStar03,
            binding.movieDetailsStar04,
            binding.movieDetailsStar05
        )

        for (index in starImageViewList.indices) {
            starImageViewList[index].setColorFilter(
                ContextCompat.getColor(
                    starImageViewList[index].context,
                    R.color.pink_light
                ), android.graphics.PorterDuff.Mode.SRC_IN
            )
        }

        val textNumberOfRatings: String =
            this.getString(R.string.numberOfRatings, movie.reviews.toString())
        binding.movieDetailsTextViewNumberOfRatings.text = textNumberOfRatings

        binding.movieDetailsTextViewOverview.text = movie.overview

        val adapter = AdapterActors(movie.actors)
        binding.movieDetailsRecyclerviewActors.adapter = adapter
        binding.movieDetailsRecyclerviewActors.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
        }

    }

    private fun getGenresName(genres: List<Genre>): String {
        val result: StringBuilder = StringBuilder()
        for (genre: Genre in genres) {
            result.append(genre.name).append(", ")
        }
        return result.dropLast(2).toString()
    }

    companion object {
        private val ARG_MOVIE_ID = "FragmentMoviesDetails_movie_id"

        fun newInstance(id: Int): FragmentMoviesDetails {
            val args = Bundle()
            args.putInt(ARG_MOVIE_ID, id)
            val fragment = FragmentMoviesDetails()
            fragment.arguments = args
            return fragment
        }
    }
}