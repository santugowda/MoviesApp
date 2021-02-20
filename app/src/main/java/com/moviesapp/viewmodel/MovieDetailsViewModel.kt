package com.moviesapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviesapp.model.MovieDetails
import com.moviesapp.network.base.NetworkResponse
import com.moviesapp.repository.OMDBRepository

class MovieDetailsViewModel : ViewModel() {

    val TAG = MovieDetailsViewModel::class.java.simpleName
    var movieRepository = OMDBRepository()

    private var movieDetailsResponse: MutableLiveData<NetworkResponse<MovieDetails>> =
        MutableLiveData()

    fun getMovieDetails(movieName: String): MutableLiveData<NetworkResponse<MovieDetails>> {
        movieDetailsResponse = movieRepository.getMovieDetails(movieName)
        Log.d(TAG, "viewmodel get movie details")
        return movieDetailsResponse
    }
}