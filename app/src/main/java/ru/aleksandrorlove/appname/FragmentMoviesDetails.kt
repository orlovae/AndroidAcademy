package ru.aleksandrorlove.appname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentMoviesDetails : Fragment(), View.OnClickListener {
    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { movie = it.getParcelable(ARG_MOVIE) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)
        val buttonBack: TextView = view.findViewById(R.id.movie_details_button_back)
        buttonBack.setOnClickListener(this)
        val backgroundTop: ImageView = view.findViewById(R.id.movie_details_background_top)
        val rars: TextView = view.findViewById(R.id.movie_details_text_view_RARS)
        val title: TextView = view.findViewById(R.id.movie_details_text_view_title)
        val tag: TextView = view.findViewById(R.id.movie_details_text_view_tag)

        val star01: ImageView = view.findViewById(R.id.movie_details_star_01)
        val star02: ImageView = view.findViewById(R.id.movie_details_star_02)
        val star03: ImageView = view.findViewById(R.id.movie_details_star_03)
        val star04: ImageView = view.findViewById(R.id.movie_details_star_04)
        val star05: ImageView = view.findViewById(R.id.movie_details_star_05)
        val reviews: TextView = view.findViewById(R.id.movie_details_text_view_reviews)
        val description: TextView = view.findViewById(R.id.movie_details_text_view_description)
        val recyclerViewActors: RecyclerView = view.findViewById(R.id.recyclerview_actors)


        movie?.let {

            backgroundTop.setImageResource(R.drawable.background_gradient)
            backgroundTop.setBackgroundResource(it.background)

            rars.text = it.RARS

            title.text = it.title

            tag.text = it.tag

            val starImageViewList = mutableListOf<ImageView>(
                star01,
                star02,
                star03,
                star04,
                star05
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
            reviews.text = textReviews

            description.text = it.description

            val actors: ArrayList<Actor> = it.actors
            val adapter = ActorsAdapter(actors)
            recyclerViewActors.adapter = adapter
            recyclerViewActors.apply { layoutManager = GridLayoutManager(requireContext(), 4) }
        }
        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.movie_details_button_back -> {
                val fragmentManager = requireActivity().supportFragmentManager
                fragmentManager.popBackStack()
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