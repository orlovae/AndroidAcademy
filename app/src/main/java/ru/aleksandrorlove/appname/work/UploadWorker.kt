package ru.aleksandrorlove.appname.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.aleksandrorlove.appname.model.Movie
import ru.aleksandrorlove.appname.network.ManagerNetwork
import ru.aleksandrorlove.appname.storage.MapperDb
import ru.aleksandrorlove.appname.storage.RepositoryDb

class UploadWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    private val managerNetwork: ManagerNetwork = ManagerNetwork()
    private val mapperDb: MapperDb = MapperDb()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            loadMovies()
            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }

    private suspend fun loadMovies() {
        val repositoryDb: RepositoryDb = RepositoryDb()
        val remoteMoviesResult = withContext(Dispatchers.IO) {
            managerNetwork.getResultListMovieFromNetwork()
        }

        if (remoteMoviesResult is ru.aleksandrorlove.appname.network.Result.Success) {
            val newMovies: List<Movie> = remoteMoviesResult.data as List<Movie>


                Log.d("UploadWorker", newMovies.size.toString())

            withContext(Dispatchers.IO) {
                repositoryDb.deleteAllToDb()
                repositoryDb.saveListMovieToDb(mapperDb.mapListFromModelToDb(newMovies))
            }
        }
    }
}