package ru.aleksandrorlove.appname.network

import ru.aleksandrorlove.appname.RepositoryNetwork
import ru.aleksandrorlove.appname.model.Actor
import ru.aleksandrorlove.appname.model.Genre
import ru.aleksandrorlove.appname.model.Movie
import kotlin.math.roundToInt

class ManagerNetwork {
    private val repositoryNetwork: RepositoryNetwork = RepositoryNetwork.Singleton.instance

    suspend fun getResultListModelOrError(): Result<Any> (
        val resultMoviesPopularNetworkOrError
            )

    suspend fun getListMovieFromNetwork(): List<Movie> {
        val moviesPopular: List<MoviePopularNetwork> =
            repositoryNetwork.getListMoviePopularNetwork()
        val moviesDetails: List<MovieDetailsNetwork> =
            repositoryNetwork.getListMovieDetailsNetwork(moviesPopular)
        val genresEntity = mapGenresToEntity(
            repositoryNetwork.getListGenreNetwork()
        )
        val genresMap = genresEntity.associateBy { it.id }

        val moviesDetailsNetworkMap = moviesDetails.associateBy { it.id }

        return moviesPopular.map { moviePopular ->
            Movie(
                id = moviePopular.id,
                title = moviePopular.title,
                overview = moviePopular.overview,
                poster = createUrlImage(ImageType.POSTER, moviePopular.poster),
                backdrop = createUrlImage(ImageType.BACKDROP, moviePopular.backdrop),
                ratings = moviesDetailsNetworkMap[moviePopular.id]?.ratings?.toInt() ?: 0,

                numberOfRatings = mapNumberOfRating(moviePopular.numberOfRatings),
                minimumAge = getMinimumAge(moviesDetails),

                runtime = moviesDetailsNetworkMap[moviePopular.id]?.runtime ?: 0,

                genres = moviePopular.genreIDS.map {
                    genresMap[it] ?: throw IllegalArgumentException("Genre not found")
                },
                actors = getListActorEntityFirstSixItem(
                    mapActorsToEntitys(
                        repositoryNetwork.getListActorsNetwork(moviePopular.id)
                    )
                )
            )
        }
    }

    private fun mapGenresToEntity(genresNetwork: List<GenreNetwork>): List<Genre> {
        return genresNetwork.map { genreNetwork ->
            Genre(
                id = genreNetwork.id,
                name = genreNetwork.name
            )
        }
    }

    // 4 - соответсвует размерам poster w500
// 1 - соответсвует размерам backdrop w780
// 0 - соответсвует размерам profile w45
    private suspend fun createUrlImage(imageType: ImageType, path: String?): String {
        if (repositoryNetwork.getConfiguration() != null) {
            val configurationImages = repositoryNetwork.getConfiguration()!!.images

            return when (imageType) {
                ImageType.POSTER -> configurationImages.baseUrlImages +
                        configurationImages.posterSizes[4] + path
                ImageType.BACKDROP -> configurationImages.baseUrlImages +
                        configurationImages.backdropSizes[1] + path
                ImageType.ACTOR -> configurationImages.baseUrlImages +
                        configurationImages.profileSizes[0] + path
            }
        } else {
            return when (imageType) {
                ImageType.POSTER -> "Заглушка для poster null"
                ImageType.BACKDROP -> "Заглушка для backdrop null"
                ImageType.ACTOR -> "Заглушка для actor null"
            }
        }
    }

    private fun mapNumberOfRating(rating: Double): Int {
        return (rating * 0.5).roundToInt()
    }

    //TODO В данной функции можно добавить агрумент language: String для выбора нащиональной системы рейтинга
    private fun getMinimumAge(moviesDetails: List<MovieDetailsNetwork>): String {
        for (index in moviesDetails.indices) {
            val result = moviesDetails[index].releaseDates.results

            for (i in result.indices) {
                if (result[i].iso_3166_1.equals("RU", true)) {
                    return result[i].release_dates_result_item[0].certification.toString()
                }
            }
        }
        return "0"
    }-

    private fun getListActorEntityFirstSixItem(actors: List<Actor>): List<Actor> {
        val sizeActorEntity: Int = if (actors.size < 6) {
            actors.size
        } else {
            7
        }
        return (actors).subList(0, sizeActorEntity)
    }

    private suspend fun mapActorsToEntitys(actorsNetwork: List<ActorNetwork>): List<Actor> {
        return actorsNetwork.map { actorNetwork ->
            Actor(
                id = actorNetwork.id,
                name = actorNetwork.name,
                picture = createUrlImage(ImageType.ACTOR, actorNetwork.picturePath)
            )
        }
    }

    fun getMovieFromNetwork(id: Int): Movie {
        return Movie(0, "", "", "", "", 0, 0, "", 0, emptyList(), emptyList())
    }


    private enum class ImageType {
        POSTER,
        BACKDROP,
        ACTOR
    }
}