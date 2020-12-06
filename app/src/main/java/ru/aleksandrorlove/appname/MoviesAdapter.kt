package ru.aleksandrorlove.appname

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MoviesAdapter(private val mMovies: List<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val cover = itemView.findViewById<ImageView>(R.id.image_view_card_view_background)
        val RARS = itemView.findViewById<TextView>(R.id.text_view_RARS)
        val like = itemView.findViewById<ImageView>(R.id.image_view_like)
        val tag = itemView.findViewById<TextView>(R.id.text_view_tag)

        //        val stars
        val reviews = itemView.findViewById<TextView>(R.id.text_view_reviews)
        val title = itemView.findViewById<TextView>(R.id.text_view_title)
        val longMovie = itemView.findViewById<TextView>(R.id.text_view_card_view_long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val movieCard = inflater.inflate(R.layout.view_holder_movie, parent, false)
        return ViewHolder(movieCard)
    }

    override fun onBindViewHolder(holder: MoviesAdapter.ViewHolder, position: Int) {
        val movie: Movie = mMovies.get(position)

        val coverImageView = holder.cover

        val RARSTextView = holder.RARS
        RARSTextView.setText(movie.RARS)

        val likeImageView = holder.like

        val tagTextView = holder.tag
        tagTextView.setText(movie.tag)

        //        val stars

        val reviewsTextView = holder.reviews
        reviewsTextView.setText(movie.reviews)

        val titleTextView = holder.title
        titleTextView.setText(movie.title)

        val longMovieTextView = holder.longMovie
        longMovieTextView.setText(movie.longMovie)

    }

    override fun getItemCount(): Int {
        return mMovies.size
    }
}