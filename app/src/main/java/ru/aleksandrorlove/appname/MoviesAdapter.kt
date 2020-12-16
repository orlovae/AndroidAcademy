package ru.aleksandrorlove.appname

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import ru.aleksandrorlove.appname.data.Genre
import ru.aleksandrorlove.appname.data.Movie
import kotlin.math.roundToInt

class MoviesAdapter(
    private val movies: List<Movie>,
    private val cellClickListener: CellClickListener
) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val poster = itemView.findViewById<ImageView>(R.id.holder_movie_image_view_poster)
        val minimumAge = itemView.findViewById<TextView>(R.id.holder_movie_text_view_minimumAge)
        val like = itemView.findViewById<ImageView>(R.id.holder_movie_image_view_like)
        val genre = itemView.findViewById<TextView>(R.id.holder_movie_text_view_genre)

        val star01 = itemView.findViewById<ImageView>(R.id.holder_movie_star_01)
        val star02 = itemView.findViewById<ImageView>(R.id.holder_movie_star_02)
        val star03 = itemView.findViewById<ImageView>(R.id.holder_movie_star_03)
        val star04 = itemView.findViewById<ImageView>(R.id.holder_movie_star_04)
        val star05 = itemView.findViewById<ImageView>(R.id.holder_movie_star_05)

        val numberOfRatings = itemView.findViewById<TextView>(R.id.holder_movie_text_view_number_of_ratings)
        val title = itemView.findViewById<TextView>(R.id.holder_movie_text_view_title)
        val runtime = itemView.findViewById<TextView>(R.id.holder_movie_text_view_runtime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val movieCard = inflater.inflate(R.layout.view_holder_movie, parent, false)
        return ViewHolder(movieCard)
    }

    override fun onBindViewHolder(holder: MoviesAdapter.ViewHolder, position: Int) {
        val context = holder.itemView.context

        val movie: Movie = movies[position]

        val posterImageView = holder.poster
//        posterImageView.setImageResource(R.drawable.poster_gradient)

        Glide.with(context)
            .load(movie.poster)
            .into(posterImageView)
//        coverImageView.setBackgroundResource(movie.cover)

        val minimumAgeTextView = holder.minimumAge
        minimumAgeTextView.text = movie.minimumAge.toString()

//        val likeImageView = holder.like
//        if (movie.like) {
//            likeImageView.setColorFilter(
//                ContextCompat.getColor(
//                    likeImageView.context,
//                    R.color.pink_light
//                ), android.graphics.PorterDuff.Mode.SRC_IN
//            )
//        }

        val genreTextView = holder.genre
        val genres = getGenres(movie.genres)
        genreTextView.text = genres

        val stars = getStars(movie.ratings) - 1
        setColorStars(holder, stars)

        val numberOfRatingsTextView = holder.numberOfRatings
        val textNumberOfRatings = context.getString(R.string.numberOfRatings,
            movie.numberOfRatings.toString())
        numberOfRatingsTextView.text = textNumberOfRatings

        val titleTextView = holder.title
        titleTextView.text = movie.title

        val runtime = holder.runtime
        val textRuntime = context.getString(R.string.long_movie, movie.runtime.toString())
        runtime.text = textRuntime

        holder.itemView.setOnClickListener { cellClickListener.onCellClickListener(movie) }

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    private fun setColorStars(holder: MoviesAdapter.ViewHolder, stars: Int) {
        val starImageViewList = mutableListOf<ImageView>(
            holder.star01,
            holder.star02,
            holder.star03,
            holder.star04,
            holder.star05
        )

        for (i in 0..stars) {
            starImageViewList.get(i).setColorFilter(
                ContextCompat.getColor(
                    starImageViewList.get(i).context,
                    R.color.pink_light
                ), android.graphics.PorterDuff.Mode.SRC_IN
            )
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
        val result = (rating * 0.5).roundToInt()
        return result
    }
}