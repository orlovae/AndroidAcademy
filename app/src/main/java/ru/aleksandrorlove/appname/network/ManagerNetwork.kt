package ru.aleksandrorlove.appname.network

import ru.aleksandrorlove.appname.RepositoryNetwork
import ru.aleksandrorlove.appname.config.NetworkConfig
import ru.aleksandrorlove.appname.model.Actor
import ru.aleksandrorlove.appname.model.Genre
import ru.aleksandrorlove.appname.model.Movie
import kotlin.math.roundToInt

class ManagerNetwork {
    private val repositoryNetwork: RepositoryNetwork = RepositoryNetwork.Singleton.instance

    suspend fun getResultListMovieFromNetwork(): Result<Any> {
        val listMovies: MutableList<Movie> = mutableListOf()

        val resultListMoviePopular = repositoryNetwork.getResultListMoviePopularNetwork()

        if (resultListMoviePopular is Result.Success) {

            val moviesPopularNetwork: MoviesPopularNetwork =
                resultListMoviePopular.data as MoviesPopularNetwork
            val moviesPopular: List<MoviePopularNetwork> = moviesPopularNetwork.moviesPopular

            moviesPopular.forEach {
                val resultMovieDetailsNetwork =
                    getResultMovieDetailsNetwork(it.id)

                if (resultMovieDetailsNetwork is Result.Success) {
                    val movie: Movie = resultMovieDetailsNetwork.data as Movie

                    listMovies.add(movie)
                }

                if (resultMovieDetailsNetwork is Result.Error) {
                    return resultMovieDetailsNetwork
                }
            }
        }

        if (resultListMoviePopular is Result.Error) {
            return resultListMoviePopular
        }

        return Result.Success(listMovies.toList())
    }


    suspend fun getResultMovieDetailsNetwork(id: Int): Result<Any> {
        var movieMutable: Movie = Movie()

        val resultMovieDetailsNetwork = repositoryNetwork.getResultMovieDetailNetwork(id)

        if (resultMovieDetailsNetwork is Result.Success) {
            val movieDetailsNetwork: MovieDetailsNetwork =
                resultMovieDetailsNetwork.data as MovieDetailsNetwork

            val resultActorsNetwork = repositoryNetwork.getResultListActorsNetwork(id)

            if (resultActorsNetwork is Result.Success) {
                val actorsNetwork: ActorsNetwork =
                    resultActorsNetwork.data as ActorsNetwork

                val listActorNetwork: List<ActorNetwork> = actorsNetwork.actors

                movieMutable = Movie(
                    id,
                    movieDetailsNetwork.title,
                    movieDetailsNetwork.overview,
                    buildUrlImage(
                        ImageType.POSTER,
                        movieDetailsNetwork.poster
                    ),
                    buildUrlImage(
                        ImageType.BACKDROP,
                        movieDetailsNetwork.backdrop
                    ),
                    convertRatingToInt(movieDetailsNetwork.ratings),
                    movieDetailsNetwork.reviews,
                    getMinimumAge(movieDetailsNetwork),
                    movieDetailsNetwork.runtime ?: 0,
                    mapGenresFromNetworkToModel(movieDetailsNetwork.genres),
                    getListActorEntityFirstSixItem(
                        mapActorsFromNetworkToModel(
                            listActorNetwork
                        )
                    )
                )
            }
            if (resultActorsNetwork is Result.Error) {
                return resultActorsNetwork
            }
        }

        if (resultMovieDetailsNetwork is Result.Error) {
            return resultMovieDetailsNetwork
        }
        val movie: Movie = movieMutable
        return Result.Success(movie)
    }

    private fun mapGenresFromNetworkToModel(genresNetwork: List<GenreNetwork>): List<Genre> {
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
    private suspend fun buildUrlImage(imageType: ImageType, path: String?): String {
        val resultConfiguration = repositoryNetwork.getResultConfiguration()
        if (resultConfiguration is Result.Success) {
            val configuration = resultConfiguration.data
            val configurationImages = configuration.images

            return when (imageType) {
                ImageType.POSTER -> configurationImages.baseUrlImages +
                        configurationImages.posterSizes[4] + path
                ImageType.BACKDROP -> configurationImages.baseUrlImages +
                        configurationImages.backdropSizes[1] + path
                ImageType.ACTOR -> configurationImages.baseUrlImages +
                        configurationImages.profileSizes[0] + path
            }
        }

        return when (imageType) {
            ImageType.POSTER -> NetworkConfig.ERROR_POSTER
            ImageType.BACKDROP -> NetworkConfig.ERROR_BACKDROP
            ImageType.ACTOR -> NetworkConfig.ERROR_ACTOR
        }
    }

    private fun convertRatingToInt(rating: Double): Int {
        return (rating * 0.5).roundToInt()
    }

    //TODO В данной функции можно добавить агрумент language: String для выбора нащиональной системы рейтинга
    private fun getMinimumAge(movieDetailsNetwork: MovieDetailsNetwork): String {
        val result = movieDetailsNetwork.releaseDates.results

        for (i in result.indices) {
            if (result[i].iso_3166_1.equals(
                    NetworkConfig.LANGUAGE_FOR_PG,
                    true
                )
            ) {
                return result[i].release_dates_result_item[0].certification.toString()
            }
        }
        return NetworkConfig.ERROR_PG
    }

    private fun getListActorEntityFirstSixItem(actors: List<Actor>): List<Actor> {
        val sizeListActor: Int = if (actors.size < 6) {
            actors.size
        } else {
            6
        }
        return (actors).subList(0, sizeListActor)
    }

    private suspend fun mapActorsFromNetworkToModel(actorsNetwork: List<ActorNetwork>): List<Actor> {
        return actorsNetwork.map { actorNetwork ->
            Actor(
                id = actorNetwork.id,
                name = actorNetwork.name,
                picture = buildUrlImage(ImageType.ACTOR, actorNetwork.picturePath)
            )
        }
    }

    private enum class ImageType {
        POSTER,
        BACKDROP,
        ACTOR
    }
}