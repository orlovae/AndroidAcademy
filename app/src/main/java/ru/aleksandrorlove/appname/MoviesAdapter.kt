package ru.aleksandrorlove.appname

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import kotlinx.coroutines.Deferred
import ru.aleksandrorlove.appname.data.Genre
import ru.aleksandrorlove.appname.data.Movie
import kotlin.math.roundToInt

class MoviesAdapter(
    private val movies: ArrayList<Movie>,
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
        val context: Context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val movieCard: View = inflater.inflate(R.layout.view_holder_movie, parent, false)
        return ViewHolder(movieCard)
    }

    override fun onBindViewHolder(holder: MoviesAdapter.ViewHolder, position: Int) {
        val context: Context = holder.itemView.context

        val movie: Movie = movies[position]

        val posterImageView: ImageView = holder.poster

        Glide.with(context)
            .load(movie.poster)
            .into(posterImageView)

        val minimumAgeTextView : TextView = holder.minimumAge
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

        val genreTextView: TextView = holder.genre
        val genres: String = getGenres(movie.genres)
        genreTextView.text = genres

        val stars: Int = getStars(movie.ratings) - 1
        setColorStars(holder, stars)

        val numberOfRatingsTextView: TextView = holder.numberOfRatings
        val textNumberOfRatings: String = context.getString(R.string.numberOfRatings,
            movie.numberOfRatings.toString())
        numberOfRatingsTextView.text = textNumberOfRatings

        val titleTextView: TextView = holder.title
        titleTextView.text = movie.title

        val runtime: TextView = holder.runtime
        val textRuntime: String = context.getString(R.string.long_movie, movie.runtime.toString())
        runtime.text = textRuntime

        holder.itemView.setOnClickListener { cellClickListener.onCellClickListener(movie) }

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    private fun setColorStars(holder: MoviesAdapter.ViewHolder, stars: Int) {
        val starImageViewList : MutableList<ImageView> = mutableListOf(
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
        for (genre: Genre in genres) {
            result.append(genre.name).append(", ")
        }
        return result.dropLast(2).toString()
    }

    private fun getStars(rating: Float): Int {
        return (rating * 0.5).roundToInt()
    }
}