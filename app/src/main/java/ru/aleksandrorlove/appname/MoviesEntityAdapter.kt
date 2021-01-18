package ru.aleksandrorlove.appname

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.aleksandrorlove.appname.Entity.GenreEntity
import ru.aleksandrorlove.appname.Entity.MovieEntity
import kotlin.math.roundToInt

class MoviesEntityAdapter(
    var moviesEntity: ArrayList<MovieEntity>,
    private val cellClickListener: CellClickListener
) :
    RecyclerView.Adapter<MoviesEntityAdapter.ViewHolder>() {

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
            itemView.findViewById<TextView>(R.id.holder_movie_text_view_number_of_ratings)
        val title = itemView.findViewById<TextView>(R.id.holder_movie_text_view_title)
        val runtime = itemView.findViewById<TextView>(R.id.holder_movie_text_view_runtime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val movieCard: View = inflater.inflate(R.layout.view_holder_movie, parent, false)
        return ViewHolder(movieCard)
    }

    override fun onBindViewHolder(holder: MoviesEntityAdapter.ViewHolder, position: Int) {
        val context: Context = holder.itemView.context

        val movieEntity: MovieEntity = moviesEntity[position]

        val posterImageView: ImageView = holder.poster

        Glide.with(context)
            .load(movieEntity.poster)
            .into(posterImageView)

        val minimumAgeTextView: TextView = holder.minimumAge
        minimumAgeTextView.text = movieEntity.minimumAge.toString()

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
        val genres: String = getGenresEntity(movieEntity.genres)
        genreTextView.text = genres

        setColorStars(holder, movieEntity.ratings)

        val numberOfRatingsTextView: TextView = holder.numberOfRatings
        val textNumberOfRatings: String = context.getString(
            R.string.numberOfRatings,
            movieEntity.numberOfRatings.toString()
        )
        numberOfRatingsTextView.text = textNumberOfRatings

        val titleTextView: TextView = holder.title
        titleTextView.text = movieEntity.title

        val runtime: TextView = holder.runtime
        val textRuntime: String =
            context.getString(R.string.long_movie, movieEntity.runtime.toString())
        runtime.text = textRuntime

        holder.itemView.setOnClickListener { cellClickListener.onCellClickListener(movieEntity.id) }

    }

    override fun getItemCount(): Int {
        return moviesEntity.size
    }

    private fun setColorStars(holder: MoviesEntityAdapter.ViewHolder, stars: Int) {
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

    private fun getGenresEntity(genresEntity: List<GenreEntity>): String {
        val result: StringBuilder = StringBuilder()
        for (genreEntity: GenreEntity in genresEntity) {
            result.append(genreEntity.name).append(", ")
        }
        return result.dropLast(2).toString()
    }
}