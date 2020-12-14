package ru.aleksandrorlove.appname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import ru.aleksandrorlove.appname.databinding.FragmentMoviesDetailsBinding

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
        val view = binding.root

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
                val fragmentManager = requireActivity().supportFragmentManager
                fragmentManager.popBackStack()
            }
        }
    }

    private fun setView() {
        binding.movieDetailsButtonBack.setOnClickListener(this)
        movie?.let {
            binding.movieDetailsBackgroundTop.setImageResource(R.drawable.background_gradient)
            binding.movieDetailsBackgroundTop.setBackgroundResource(it.background)

            binding.movieDetailsTextViewRARS.text = it.RARS

            binding.movieDetailsTextViewTitle.text = it.title

            binding.movieDetailsTextViewTag.text = it.tag

            val starImageViewList = listOf<ImageView>(
                binding.movieDetailsStar01,
                binding.movieDetailsStar02,
                binding.movieDetailsStar03,
                binding.movieDetailsStar04,
                binding.movieDetailsStar05
            )

            val stars = it.stars - 1
            for (i in 0..stars) {
                starImageViewList.get(i).setColorFilter(
                    ContextCompat.getColor(
                        starImageViewList.get(i).context,
                        R.color.pink_light
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                )
            }

            val textReviews = this.getString(R.string.reviews, it.reviews)
            binding.movieDetailsTextViewReviews.text = textReviews

            binding.movieDetailsTextViewDescription.text = it.description

            val actors: ArrayList<Actor> = it.actors
            val adapter = ActorsAdapter(actors)
            binding.movieDetailsRecyclerviewActors.adapter = adapter
            binding.movieDetailsRecyclerviewActors.apply {
                layoutManager = GridLayoutManager(requireContext(), 4)
            }
        }
    }

    companion object {
        private val ARG_MOVIE = "FragmentMoviesDetails_movie"

        fun newInstance(movie: Movie): FragmentMoviesDetails {
            val args: Bundle = Bundle()
            args.putParcelable(ARG_MOVIE, movie)
            val fragment = FragmentMoviesDetails()
            fragment.arguments = args
            return fragment
        }
    }
}