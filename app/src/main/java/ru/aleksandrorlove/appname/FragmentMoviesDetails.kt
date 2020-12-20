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
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import ru.aleksandrorlove.appname.data.Actor
import ru.aleksandrorlove.appname.data.Genre
import ru.aleksandrorlove.appname.data.Movie
import ru.aleksandrorlove.appname.databinding.FragmentMoviesDetailsBinding
import kotlin.math.roundToInt

class FragmentMoviesDetails : Fragment(), View.OnClickListener {
    private var movie: Movie? = null
    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { movie = it.getParcelable(ARG_MOVIE) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        val view: ScrollView = binding.root

        setView()

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

    private fun setView() {
        binding.movieDetailsButtonBack.setOnClickListener(this)
        movie?.let {
            binding.movieDetailsBackgroundTop.setImageResource(R.drawable.background_gradient)
            Glide.with(requireActivity())
                .load(it.backdrop)
                .into(binding.movieDetailsBackgroundTop)

            binding.movieDetailsTextViewMinimumAge.text = it.minimumAge.toString()

            binding.movieDetailsTextViewTitle.text = it.title

            val genres : String = getGenres(it.genres)
            binding.movieDetailsTextViewGenre.text = genres

            val starImageViewList : List<ImageView> = listOf(
                binding.movieDetailsStar01,
                binding.movieDetailsStar02,
                binding.movieDetailsStar03,
                binding.movieDetailsStar04,
                binding.movieDetailsStar05
            )

            val stars = getStars(it.ratings) - 1
            for (i in 0..stars) {
                starImageViewList.get(i).setColorFilter(
                    ContextCompat.getColor(
                        starImageViewList.get(i).context,
                        R.color.pink_light
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                )
            }

            val textNumberOfRatings : String = this.getString(R.string.numberOfRatings, it.numberOfRatings.toString())
            binding.movieDetailsTextViewNumberOfRatings.text = textNumberOfRatings

            binding.movieDetailsTextViewOverview.text = it.overview

            val actors: ArrayList<Actor> = it.actors as ArrayList<Actor>
            val adapter = ActorsAdapter(actors)
            binding.movieDetailsRecyclerviewActors.adapter = adapter
            binding.movieDetailsRecyclerviewActors.apply {
                layoutManager = GridLayoutManager(requireContext(), 4)
            }
        }
    }

    private fun getGenres(genres: List<Genre>): String {
        val result: StringBuilder = StringBuilder()
        for (genre in genres) {
            result.append(genre.name).append(", ")
        }
        return result.dropLast(2).toString()
    }

    private fun getStars(rating: Float): Int {
        return (rating * 0.5).roundToInt()
    }

    companion object {
        private val ARG_MOVIE = "FragmentMoviesDetails_movie"

        fun newInstance(movie: Movie): FragmentMoviesDetails {
            val args = Bundle()
            args.putParcelable(ARG_MOVIE, movie)
            val fragment = FragmentMoviesDetails()
            fragment.arguments = args
            return fragment
        }
    }
}