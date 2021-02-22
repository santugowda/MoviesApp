package com.moviesapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.moviesapp.db.MovieDatabase
import com.moviesapp.model.MovieDetails
import com.moviesapp.network.base.NetworkResponse
import com.moviesapp.repository.OMDBRepository

class MoviesViewModel(val app : Application) : AndroidViewModel(app) {

    val TAG = MoviesViewModel::class.java.simpleName
    private val movieDetailsDao =  MovieDatabase.getInstance(app).movieDetailsDao()
    var movieRepository = OMDBRepository(movieDetailsDao)

    private var movieDetailsResponse: MutableLiveData<NetworkResponse<MovieDetails>> =
        MutableLiveData()

    val allFavMovies: LiveData<List<MovieDetails>> = movieRepository.allFavMovieList.asLiveData()
    val allFavMovieListOnDemand: List<MovieDetails> = movieRepository.allFavMovieListOnDemand

    fun insertMovieDetailsToFav(movieDetails : MovieDetails) {
        movieRepository.insertMovie(movieDetails)
    }

    fun deleteMovieDetailsFromFav(movieDetails : MovieDetails) {
        movieRepository.deleteMovie(movieDetails)
    }

    fun getMovieDetails(movieName: String): MutableLiveData<NetworkResponse<MovieDetails>> {
        movieDetailsResponse = movieRepository.getMovieDetails(movieName)
        Log.d(TAG, "viewmodel get movie details")
        return movieDetailsResponse
    }
}