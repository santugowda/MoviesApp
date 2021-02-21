package com.moviesapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviesapp.model.MovieDetails
import com.moviesapp.model.MovieSearch
import com.moviesapp.network.base.NetworkResponse
import com.moviesapp.repository.OMDBRepository

class MoviesViewModel : ViewModel() {

    val TAG = MoviesViewModel::class.java.simpleName
    var movieRepository = OMDBRepository()

    private var movieDetailsResponse: MutableLiveData<NetworkResponse<MovieDetails>> =
        MutableLiveData()

    private var movieSearchResponse: MutableLiveData<NetworkResponse<MovieSearch>> =
        MutableLiveData()

    fun getMovieDetails(movieName: String): MutableLiveData<NetworkResponse<MovieDetails>> {
        movieDetailsResponse = movieRepository.getMovieDetails(movieName)
        Log.d(TAG, "viewmodel get movie details")
        return movieDetailsResponse
    }

    fun searchMovie(movieName: String, pageNum : Int): MutableLiveData<NetworkResponse<MovieSearch>> {
        movieSearchResponse = movieRepository.getSearchMovie(movieName, pageNum)
        Log.d(TAG, "viewmodel get movie serach details")
        return movieSearchResponse
    }
}