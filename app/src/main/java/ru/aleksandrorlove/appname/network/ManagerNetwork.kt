package ru.aleksandrorlove.appname.network

import ru.aleksandrorlove.appname.RepositoryNetwork2
import ru.aleksandrorlove.appname.config.NetworkConfig
import ru.aleksandrorlove.appname.model.Actor
import ru.aleksandrorlove.appname.model.Genre
import ru.aleksandrorlove.appname.model.Movie
import kotlin.math.roundToInt

class ManagerNetwork {
    private val repositoryNetwork2: RepositoryNetwork2 = RepositoryNetwork2.Singleton.instance

    suspend fun getResultListMovieFromNetwork(): Result<Any> {
        val listMovies: MutableList<Movie> = mutableListOf()

        val resultGenres = repositoryNetwork2.getResultListGenreNetwork()

        if (resultGenres is Result.Success) {
            val genresNetwork: GenresNetwork = resultGenres.data as GenresNetwork

            val resultListMoviePopular = repositoryNetwork2.getResultListMoviePopularNetwork()

            if (resultListMoviePopular is Result.Success) {

                val moviesPopularNetwork: MoviesPopularNetwork
                        = resultListMoviePopular.data as MoviesPopularNetwork
                val moviesPopular: List<MoviePopularNetwork> = moviesPopularNetwork.moviesPopular

                val resultListMovieDetailsNetwork =
                    getResultListMovieDetailsNetwork(moviesPopular)

                if (resultListMovieDetailsNetwork is Result.Success) {
                    val moviesDetails: List<MovieDetailsNetwork> =
                        resultListMovieDetailsNetwork.data as List<MovieDetailsNetwork>

                    val genres = mapGenresFromNetworkToModel(genresNetwork.genres)
                    val genresMap = genres.associateBy { it.id }

                    for (index in moviesPopular.indices) {
                        val resultActorsNetwork = repositoryNetwork2.getResultListActorsNetwork(
                            moviesPopular[index].id
                        )

                        if (resultActorsNetwork is Result.Success) {
                            val actorsNetwork: ActorsNetwork =
                                resultActorsNetwork.data as ActorsNetwork

                            val listActorNetwork: List<ActorNetwork> = actorsNetwork.actors

                            listMovies.add(
                                Movie(
                                    moviesPopular[index].id,
                                    moviesPopular[index].title,
                                    moviesPopular[index].overview,
                                    buildUrlImage(
                                        ImageType.POSTER,
                                        moviesPopular[index].poster
                                    ),
                                    buildUrlImage(
                                        ImageType.BACKDROP,
                                        moviesPopular[index].backdrop
                                    ),
                                    moviesDetails[index].ratings.toInt(),
                                    convertNumberOfRatingToInt(moviesPopular[index].numberOfRatings),
                                    getMinimumAge(moviesDetails[index]),
                                    moviesDetails[index].runtime ?: 0,
                                    moviesPopular[index].genreIDS.map {
                                        genresMap[it]
                                            ?: throw IllegalArgumentException("Genre not found")
                                    },
                                    getListActorEntityFirstSixItem(
                                        mapActorsFromNetworkToModel(
                                            listActorNetwork
                                        )
                                    )
                                )
                            )
                        }

                        if (resultActorsNetwork is Result.Error) {
                            return resultActorsNetwork
                        }
                    }
                }

                if (resultListMovieDetailsNetwork is Result.Error) {
                    return resultListMovieDetailsNetwork
                }
            }

            if (resultListMoviePopular is Result.Error) {
                return resultListMoviePopular
            }
        }

        if (resultGenres is Result.Error) {
            return resultGenres
        }
        return Result.Success(listMovies)
    }


    suspend fun getResultListMovieDetailsNetwork(moviesPopular: List<MoviePopularNetwork>):
            Result<Any> {
        val listMovieDetailsNetwork:
                MutableList<MovieDetailsNetwork> = mutableListOf()
        moviesPopular.forEach {

            val resultMovieDetailsNetwork = repositoryNetwork2.getResultMovieDetailNetwork(it.id)

            if (resultMovieDetailsNetwork is Result.Success) {
                listMovieDetailsNetwork.add(resultMovieDetailsNetwork.data as MovieDetailsNetwork)
            }

            if (resultMovieDetailsNetwork is Result.Error) {
                return resultMovieDetailsNetwork
            }
        }
        return Result.Success(listMovieDetailsNetwork)
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
        val resultConfiguration = repositoryNetwork2.getResultConfiguration()
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

    private fun convertNumberOfRatingToInt(rating: Double): Int {
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

    fun getMovieFromNetwork(id: Int): Movie {
        return Movie(0, "", "", "", "", 0, 0, "", 0, emptyList(), emptyList())
    }


    private enum class ImageType {
        POSTER,
        BACKDROP,
        ACTOR
    }
}