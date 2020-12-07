package ru.aleksandrorlove.appname

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


class MoviesAdapter(private val mMovies: List<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val cover = itemView.findViewById<ImageView>(R.id.holder_movie_image_view_cover)
        val RARS = itemView.findViewById<TextView>(R.id.holder_movie_text_view_RARS)
        val like = itemView.findViewById<ImageView>(R.id.holder_movie_image_view_like)
        val tag = itemView.findViewById<TextView>(R.id.holder_movie_text_view_tag)

        val star01 = itemView.findViewById<ImageView>(R.id.holder_movie_star_01)
        val star02 = itemView.findViewById<ImageView>(R.id.holder_movie_star_02)
        val star03 = itemView.findViewById<ImageView>(R.id.holder_movie_star_03)
        val star04 = itemView.findViewById<ImageView>(R.id.holder_movie_star_04)
        val star05 = itemView.findViewById<ImageView>(R.id.holder_movie_star_05)

        val reviews = itemView.findViewById<TextView>(R.id.holder_movie_text_view_reviews)
        val title = itemView.findViewById<TextView>(R.id.holder_movie_text_view_title)
        val longMovie = itemView.findViewById<TextView>(R.id.holder_movie_text_view_long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val movieCard = inflater.inflate(R.layout.view_holder_movie, parent, false)
        return ViewHolder(movieCard)
    }

    override fun onBindViewHolder(holder: MoviesAdapter.ViewHolder, position: Int) {
        val context = holder.itemView.context

        val movie: Movie = mMovies.get(position)

        val coverImageView = holder.cover
        coverImageView.setImageResource(R.drawable.cover_gradient)
        coverImageView.setBackgroundResource(movie.cover)

        val RARSTextView = holder.RARS
        RARSTextView.text = movie.RARS

        val likeImageView = holder.like
        if (movie.like) {
            likeImageView.setColorFilter(
                ContextCompat.getColor(
                    likeImageView.context,
                    R.color.pink_light
                ), android.graphics.PorterDuff.Mode.SRC_IN
            )
        }

        val tagTextView = holder.tag
        tagTextView.text = movie.tag

        setColorStars(holder, movie.stars - 1)

        val reviewsTextView = holder.reviews
        val textReviews = context.getString(R.string.reviews, movie.reviews)
        reviewsTextView.text = textReviews

        val titleTextView = holder.title
        titleTextView.text = movie.title

        val longMovieTextView = holder.longMovie
        val textLongMovie = context.getString(R.string.long_movie, movie.longMovie)
        longMovieTextView.text = textLongMovie

    }

    override fun getItemCount(): Int {
        return mMovies.size
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
}