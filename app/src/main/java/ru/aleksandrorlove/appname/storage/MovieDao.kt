package ru.aleksandrorlove.appname.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.aleksandrorlove.appname.model.entity.MovieDb

@Dao
interface MovieDao {
    @Query("SELECT *FROM Movies")
    fun getAll(): List<MovieDb>

    @Insert
    fun insertAll(movies: List<MovieDb>)

    @Query("DELETE FROM Movies")
    fun deleteAll()
}