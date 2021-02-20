package com.moviesapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.moviesapp.model.MovieDetails
import com.moviesapp.network.base.NetworkResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OMDBRepository : BaseRepository() {

    private val TAG = OMDBRepository::class.java.simpleName
    private val movieDetails = MutableLiveData<NetworkResponse<MovieDetails>>()

    fun getMovieDetails(movie : String) : MutableLiveData<NetworkResponse<MovieDetails>> {
        omdbApiServices.getMovieDetails(movie).enqueue(object : Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                if(response.isSuccessful){
                    Log.d(TAG, "Retrofit call successful with response " + response.body().toString())
                    movieDetails.postValue(NetworkResponse.success(response.body()))
                }
            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                Log.d(TAG, "Retrofit call failure with error " + t.localizedMessage)
                movieDetails.postValue(NetworkResponse.failure(t.localizedMessage, null))
            }
        })
        return movieDetails
    }

}