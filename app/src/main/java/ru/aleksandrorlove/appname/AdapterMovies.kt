package ru.aleksandrorlove.appname

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.aleksandrorlove.appname.model.Genre
import ru.aleksandrorlove.appname.model.Movie

class AdapterMovies(
    var movies: List<Movie>,
    private val cellClickListener: CellClickListener
) :
    RecyclerView.Adapter<AdapterMovies.ViewHolder>() {

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

        val numberOfRatings =
            itemView.findViewById<TextView>(R.id.holder_movie_text_view_reviews)
        val title = itemView.findViewById<TextView>(R.id.holder_movie_text_view_title)
        val runtime = itemView.findViewById<TextView>(R.id.holder_movie_text_view_runtime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val movieCard: View = inflater.inflate(R.layout.view_holder_movie, parent, false)
        return ViewHolder(movieCard)
    }

    override fun onBindViewHolder(holder: AdapterMovies.ViewHolder, position: Int) {
        val context: Context = holder.itemView.context

        val movie: Movie = movies[position]

        val posterImageView: ImageView = holder.poster

        Log.d("Adapter", "poster = " + movie.poster)



        Glide.with(context)
            .load(movie.poster)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    p0: GlideException?,
                    p1: Any?,
                    p2: Target<Drawable>?,
                    p3: Boolean
                ): Boolean {
                    Log.d("Glide", "onLoadFailed")
                    return false
                }

                override fun onResourceReady(
                    p0: Drawable?,
                    p1: Any?,
                    p2: Target<Drawable>?,
                    p3: DataSource?,
                    p4: Boolean
                ): Boolean {
                    Log.d("Glide", "OnResourceReady")
                    //do something when picture already loaded
                    return false
                }
            })
            .into(posterImageView)

        val minimumAgeTextView: TextView = holder.minimumAge
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
        val genres: String = covertListGenreToString(movie.genres)
        genreTextView.text = genres

        setColorStars(holder, movie.ratings)

        val numberOfRatingsTextView: TextView = holder.numberOfRatings
        val textNumberOfRatings: String = context.getString(
            R.string.numberOfRatings,
            movie.reviews.toString()
        )
        numberOfRatingsTextView.text = textNumberOfRatings

        val titleTextView: TextView = holder.title
        titleTextView.text = movie.title

        val runtime: TextView = holder.runtime
        val textRuntime: String =
            context.getString(R.string.long_movie, movie.runtime.toString())
        runtime.text = textRuntime

        holder.itemView.setOnClickListener { cellClickListener.onCellClickListener(movie.id) }

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    private fun setColorStars(holder: AdapterMovies.ViewHolder, stars: Int) {
        val starImageViewList: MutableList<ImageView> = mutableListOf(
            holder.star01,
            holder.star02,
            holder.star03,
            holder.star04,
            holder.star05
        )

        for (index in starImageViewList.indices) {
            starImageViewList[index].setColorFilter(
                ContextCompat.getColor(
                    starImageViewList[index].context,
                    R.color.pink_light
                ), android.graphics.PorterDuff.Mode.SRC_IN
            )
        }
    }

    private fun covertListGenreToString(genres: List<Genre>): String {
        val result: StringBuilder = StringBuilder()
        for (genre: Genre in genres) {
            result.append(genre.name).append(", ")
        }
        return result.dropLast(2).toString()
    }
}