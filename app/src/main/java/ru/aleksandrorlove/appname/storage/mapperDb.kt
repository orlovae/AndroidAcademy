package ru.aleksandrorlove.appname.storage

import ru.aleksandrorlove.appname.model.Actor
import ru.aleksandrorlove.appname.model.Genre
import ru.aleksandrorlove.appname.model.Movie
import ru.aleksandrorlove.appname.model.entity.MovieDb

class mapperDb {
    fun mapFromDbToModel(moviesDb: List<MovieDb>): List<Movie>{
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

    private fun getGenres(movieDb: MovieDb): List<Genre> {
        val genres = mutableListOf<Genre>()
        val idGenresDb: List<Int> = movieDb.idGenres
        val genresDb: List<String> = movieDb.genres
        for (index in idGenresDb.indices) {
            genres.add(index,
                Genre(
                    idGenresDb[index],
                    genresDb[index])
            )
        }
        return genres.toList()
    }

    private fun getActors(movieDb: MovieDb): List<Actor> {
        val actors = mutableListOf<Actor>()
        val idActorDb: List<Int> = movieDb.idActors
        val actorsNameDb: List<String> = movieDb.actorsName
        val actorsPictureDb: List<String> = movieDb.actorsPicture

        for (index in idActorDb.indices) {
            actors.add(index,
                Actor(
                    idActorDb[index],
                    actorsNameDb[index],
                    actorsPictureDb[index])
            )
        }
        return actors.toList()
    }
}