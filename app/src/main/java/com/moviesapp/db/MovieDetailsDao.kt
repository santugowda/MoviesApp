package com.moviesapp.db

import androidx.paging.DataSource
import androidx.room.*
import com.moviesapp.model.MovieDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDetailsDao {

    @Query("SELECT * FROM moviesDetails")
    fun getMovieDetailsList(): Flow<List<MovieDetails>>

    @Query("SELECT * FROM moviesDetails")
    fun getMovieDetailsListOnDemand(): List<MovieDetails>

    @Query("SELECT * FROM moviesDetails")
    fun getMovieWithTitle() : DataSource.Factory<Int, MovieDetails>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movieDetails : MovieDetails)

    @Query("DELETE FROM moviesDetails WHERE title = :title")
    fun deleteMovie(title : String)

}