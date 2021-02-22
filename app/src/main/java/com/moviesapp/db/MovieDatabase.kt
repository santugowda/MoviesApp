package com.moviesapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.moviesapp.model.MovieDetails

/**
 * Database class with name as "movieDatabase"
 */
@Database(entities = [MovieDetails::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDetailsDao(): MovieDetailsDao

    companion object {
        private var dbInstance: MovieDatabase? = null
        const val dbName = "movieDatabase"

        fun getInstance(
            context: Context): MovieDatabase {
            // if the dbInstance is not null, then return it,
            // if it is, then create the database
            return dbInstance
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java, dbName)
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    dbInstance = instance
                    // return instance
                    instance
                }
        }
    }
}