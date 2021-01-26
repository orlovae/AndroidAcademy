package ru.aleksandrorlove.appname.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.aleksandrorlove.appname.storage.Contract

@Entity(tableName = Contract.Movie.TABLE_NAME)
data class MovieDb(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_ID)
    val id: Int,
    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_TITLE)
    val title: String,
    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_OVERVIEW)
    val overview: String,
    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_POSTER)
    val poster: String,
    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_BACKDROP)
    val backdrop: String,
    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_RATINGS)
    val ratings: Int,
    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_NUMBER_OF_RAITING)
    val numberOfRatings: Int,
    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_MINIMUM_AGE)
    val minimumAge: String,
    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_RUNTIME)
    val runtime: Int,
    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_ID_GENRES)
    val genresId: List<Int>,
    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_GENRES)
    val genres: List<String>,
    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_ID_ACTORS)
    val actorsId: List<Int>,
    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_ACTORS_NAME)
    val actorsName: List<String>,
    @ColumnInfo(name = Contract.Movie.COLUMN_NAME_ACTORS_PICTURE)
    val actorsPicture: List<String>
)