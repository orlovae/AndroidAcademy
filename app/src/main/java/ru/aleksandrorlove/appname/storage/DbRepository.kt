package ru.aleksandrorlove.appname.storage

import android.content.Context
import ru.aleksandrorlove.appname.model.entity.MovieDb

class DbRepository() {
    private val db = AppDataBase.instance

    fun readMoviesFromDb() : List<MovieDb> {
        return db.MoviesDao().getAll()
    }

    fun saveMovieToDb(moviesDb: List<MovieDb>){
        db.MoviesDao().insertAll(moviesDb)
    }

    fun deleteAllToDb() {
        db.MoviesDao().deleteAll()
    }
}