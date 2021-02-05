package ru.aleksandrorlove.appname.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.aleksandrorlove.appname.model.Movie
import ru.aleksandrorlove.appname.model.entity.MovieDb

@Dao
interface MovieDao {
    @Query("SELECT * FROM Movies")
    fun getAll(): List<MovieDb>

    @Query("SELECT * FROM Movies WHERE id = :id")
    fun getMovie(id: Int): MovieDb

    @Update
    fun update(movieDb: MovieDb)

    @Insert
    fun insertAll(moviesDb: List<MovieDb>)

    @Query("DELETE FROM Movies")
    fun deleteAll()
}