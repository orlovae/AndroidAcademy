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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import ru.aleksandrorlove.appname.Entity.ActorEntity
import ru.aleksandrorlove.appname.Entity.GenreEntity
import ru.aleksandrorlove.appname.Entity.MovieEntity
import ru.aleksandrorlove.appname.databinding.FragmentMoviesDetailsBinding

class FragmentMoviesDetails : Fragment(), View.OnClickListener {
    private var id: Int? = null
    private var movieEntity: MovieEntity? = null
    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { id = it.getInt(ARG_MOVIE_ID) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vm: ViewModelMovieDetails =
            ViewModelProvider(this).get(ViewModelMovieDetails::class.java)
        id?.let { vm.onPressItemRecyclerView(it) }
        vm.liveDataMovie.observe(
            viewLifecycleOwner,
            Observer<MovieEntity> {
                it?.let {
                    movieEntity = vm.liveDataMovie.value
                    setView()
                }
            })
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

    private fun setView() {
        binding.movieDetailsButtonBack.setOnClickListener(this)
        movieEntity?.let {
            binding.movieDetailsBackgroundTop.setImageResource(R.drawable.background_gradient)
            Glide.with(requireActivity())
                .load(it.backdrop)
                .into(binding.movieDetailsBackgroundTop)

            binding.movieDetailsTextViewMinimumAge.text = it.minimumAge.toString()

            binding.movieDetailsTextViewTitle.text = it.title

            val genres: String = getGenresEntity(it.genres)
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
                this.getString(R.string.numberOfRatings, it.numberOfRatings.toString())
            binding.movieDetailsTextViewNumberOfRatings.text = textNumberOfRatings

            binding.movieDetailsTextViewOverview.text = it.overview

            val adapter = ActorsEntityAdapter(it.actors)
            binding.movieDetailsRecyclerviewActors.adapter = adapter
            binding.movieDetailsRecyclerviewActors.apply {
                layoutManager = GridLayoutManager(requireContext(), 4)
            }
        }
    }

    private fun getGenresEntity(genresEntity: List<GenreEntity>): String {
        val result: StringBuilder = StringBuilder()
        for (genreEntity: GenreEntity in genresEntity) {
            result.append(genreEntity.name).append(", ")
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