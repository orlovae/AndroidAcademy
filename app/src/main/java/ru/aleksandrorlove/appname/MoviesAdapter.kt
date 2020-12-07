package ru.aleksandrorlove.appname

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MoviesAdapter(private val mMovies: List<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val cover = itemView.findViewById<ImageView>(R.id.holder_movie_image_view_cover)
        val RARS = itemView.findViewById<TextView>(R.id.holder_movie_text_view_RARS)
        val like = itemView.findViewById<ImageView>(R.id.holder_movie_image_view_like)
        val tag = itemView.findViewById<TextView>(R.id.holder_movie_text_view_tag)

        //        val stars
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
        coverImageView.setImageResource(movie.cover)

        val RARSTextView = holder.RARS
        RARSTextView.setText(movie.RARS)

        val likeImageView = holder.like
        if (!movie.like) {
            likeImageView.setImageResource(R.drawable.ic_like)
        }
        //TODO переписать, если тру то красное сердце, иначе, белое. Посмотреть образец, м.б. делать
        // заливку через цвет

        val tagTextView = holder.tag
        tagTextView.setText(movie.tag)

        //        val stars

        val reviewsTextView = holder.reviews
        val textReviews = context.getString(R.string.reviews, movie.reviews)
        reviewsTextView.setText(textReviews)

        val titleTextView = holder.title
        titleTextView.setText(movie.title)

        val longMovieTextView = holder.longMovie
        val textLongMovie = context.getString(R.string.long_movie , movie.longMovie)
        longMovieTextView.setText(textLongMovie)

    }

    override fun getItemCount(): Int {
        return mMovies.size
    }
}