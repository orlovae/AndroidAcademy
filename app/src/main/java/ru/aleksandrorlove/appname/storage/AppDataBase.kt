package ru.aleksandrorlove.appname.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.aleksandrorlove.appname.App
import ru.aleksandrorlove.appname.model.entity.MovieDb

@Database(entities = [MovieDb::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun MoviesDao(): MovieDao

    companion object {

        val instance: AppDataBase by lazy {
            Room.databaseBuilder(
                App.Singletone.instance,
                AppDataBase::class.java,
                Contract.DATABASE_NAME
            ).build()
        }
    }
}