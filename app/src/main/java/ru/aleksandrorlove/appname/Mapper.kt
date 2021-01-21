package ru.aleksandrorlove.appname

import ru.aleksandrorlove.appname.Entity.ActorEntity
import ru.aleksandrorlove.appname.Entity.GenreEntity
import ru.aleksandrorlove.appname.Entity.MovieEntity
import ru.aleksandrorlove.appname.network.ActorNetwork
import ru.aleksandrorlove.appname.network.GenreNetwork
import ru.aleksandrorlove.appname.network.MovieDetailsNetwork
import ru.aleksandrorlove.appname.network.MoviePopularNetwork
import kotlin.math.roundToInt

class Mapper {
    private val networkRepository: NetworkRepository = NetworkRepository.Singleton.instance

    suspend fun mapListMoviePopularNetworkToListMoviesEntity(): List<MovieEntity> {
        val moviesPopular: List<MoviePopularNetwork> =
            networkRepository.getListMoviePopularNetwork()
        val moviesDetails: List<MovieDetailsNetwork> =
            networkRepository.getListMovieDetailsNetwork(moviesPopular)
        val genresEntity = mapGenresToEntity(
            networkRepository.getListGenreNetwork()
        )
        val genresMap = genresEntity.associateBy { it.id }

        val moviesDetailsNetworkMap = moviesDetails.associateBy { it.id }

        return moviesPopular.map { moviePopular ->
            MovieEntity(
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
                        networkRepository.getListActorsNetwork(moviePopular.id)
                    )
                )
            )
        }
    }

    private fun mapGenresToEntity(genresNetwork: List<GenreNetwork>): List<GenreEntity> {
        return genresNetwork.map { genreNetwork ->
            GenreEntity(
                id = genreNetwork.id,
                name = genreNetwork.name
            )
        }
    }

    // 4 - соответсвует размерам poster w500
// 1 - соответсвует размерам backdrop w780
// 0 - соответсвует размерам profile w45
    private suspend fun createUrlImage(imageType: ImageType, path: String?): String {
        if (networkRepository.getConfiguration() != null) {
            val configurationImages = networkRepository.getConfiguration()!!.images

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
    }

    private fun getListActorEntityFirstSixItem(actors: List<ActorEntity>): List<ActorEntity> {
        val sizeActorEntity: Int = if (actors.size < 6) {
            actors.size
        } else {
            7
        }
        return (actors).subList(0, sizeActorEntity)
    }

    private suspend fun mapActorsToEntitys(actorsNetwork: List<ActorNetwork>): List<ActorEntity> {
        return actorsNetwork.map { actorNetwork ->
            ActorEntity(
                id = actorNetwork.id,
                name = actorNetwork.name,
                picture = createUrlImage(ImageType.ACTOR, actorNetwork.picturePath)
            )
        }
    }

    fun getMovieEntity(id: Int): MovieEntity {
        return MovieEntity(0, "", "", "", "", 0, 0, "", 0, emptyList(), emptyList())
    }


    private enum class ImageType {
        POSTER,
        BACKDROP,
        ACTOR
    }
}