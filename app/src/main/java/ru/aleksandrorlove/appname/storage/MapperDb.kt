package ru.aleksandrorlove.appname.storage

import ru.aleksandrorlove.appname.model.Actor
import ru.aleksandrorlove.appname.model.Genre
import ru.aleksandrorlove.appname.model.Movie
import ru.aleksandrorlove.appname.model.entity.MovieDb

class MapperDb {
    fun mapListFromDbToModel(moviesDb: List<MovieDb>): List<Movie> {
        val movies: MutableList<Movie> = mutableListOf()
        for (item in moviesDb) {
            movies.add(mapMovieFromDbToModel(item))
        }
        return movies.toList()
    }

    fun mapListFromModelToDb(movies: List<Movie>): List<MovieDb> {
        val moviesDb: MutableList<MovieDb> = mutableListOf()
        movies.forEach {
            moviesDb.add(mapMovieFromModelToDb(it))
        }
        return moviesDb.toList()
    }

    fun mapMovieFromModelToDb(movie: Movie): MovieDb {
        return MovieDb(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            poster = movie.poster,
            backdrop = movie.backdrop,
            ratings = movie.ratings,
            numberOfRatings = movie.reviews,
            minimumAge = movie.minimumAge,
            runtime = movie.runtime,
            releaseDate = movie.releaseDate,
            genreId = movie.genres.map { genre -> genre.id },
            genreName = movie.genres.map { genre -> genre.name },
            actorId = movie.actors.map { actor -> actor.id },
            actorName = movie.actors.map { actor -> actor.name },
            actorPicture = movie.actors.map { actor -> actor.picture }
        )
    }

    fun mapMovieFromDbToModel(movieDb: MovieDb): Movie {
        return Movie(
            id = movieDb.id,
            title = movieDb.title,
            overview = movieDb.overview,
            poster = movieDb.poster,
            backdrop = movieDb.backdrop,
            ratings = movieDb.ratings,
            reviews = movieDb.numberOfRatings,
            minimumAge = movieDb.minimumAge,
            runtime = movieDb.runtime,
            releaseDate = movieDb.releaseDate,
            genres = getGenres(movieDb),
            actors = getActors(movieDb)
        )
    }

    private fun getGenres(movieDb: MovieDb): List<Genre> {
        val genres = mutableListOf<Genre>()
        val genresId: List<Int> = movieDb.genreId
        val genresDb: List<String> = movieDb.genreName

        for (index in genresId.indices) {
            genres.add(
                index,
                Genre(
                    genresId[index],
                    genresDb[index]
                )
            )
        }
        return genres.toList()
    }

    private fun getActors(movieDb: MovieDb): List<Actor> {
        val actors = mutableListOf<Actor>()
        val actorsId: List<Int> = movieDb.actorId
        val actorsNameDb: List<String> = movieDb.actorName
        val actorsPictureDb: List<String> = movieDb.actorPicture

        for (index in actorsId.indices) {
            actors.add(
                index,
                Actor(
                    actorsId[index],
                    actorsNameDb[index],
                    actorsPictureDb[index]
                )
            )
        }
        return actors.toList()
    }
}
