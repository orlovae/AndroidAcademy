package ru.aleksandrorlove.appname.storage

import ru.aleksandrorlove.appname.model.Actor
import ru.aleksandrorlove.appname.model.Genre
import ru.aleksandrorlove.appname.model.Movie
import ru.aleksandrorlove.appname.model.entity.MovieDb

class MapperDb {
    fun mapFromDbToModel(moviesDb: List<MovieDb>): List<Movie> {
        return moviesDb.map { movieDb ->
            Movie(
                id = movieDb.id,
                title = movieDb.title,
                overview = movieDb.overview,
                poster = movieDb.poster,
                backdrop = movieDb.backdrop,
                ratings = movieDb.ratings,
                numberOfRatings = movieDb.numberOfRatings,
                minimumAge = movieDb.minimumAge,
                runtime = movieDb.runtime,
                genres = getGenres(movieDb),
                actors = getActors(movieDb)
            )
        }
    }

    fun mapFromModelToDb(movies: List<Movie>): List<MovieDb> {
        return movies.map { movie ->
            MovieDb(
                id = movie.id,
                title = movie.title,
                overview = movie.overview,
                poster = movie.poster,
                backdrop = movie.backdrop,
                ratings = movie.ratings,
                numberOfRatings = movie.numberOfRatings,
                minimumAge = movie.minimumAge,
                runtime = movie.runtime,
                genresId = movie.genres.map { genre -> genre.id },
                genres = movie.genres.map { genre -> genre.name },
                actorsId = movie.actors.map { actor -> actor.id },
                actorsName = movie.actors.map { actor -> actor.name },
                actorsPicture = movie.actors.map { actor -> actor.picture }

            )
        }
    }

    private fun getGenres(movieDb: MovieDb): List<Genre> {
        val genres = mutableListOf<Genre>()
        val genresId: List<Int> = movieDb.genresId
        val genresDb: List<String> = movieDb.genres
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
        val actorsId: List<Int> = movieDb.actorsId
        val actorsNameDb: List<String> = movieDb.actorsName
        val actorsPictureDb: List<String> = movieDb.actorsPicture

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
