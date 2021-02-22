package com.moviesapp.repository

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.moviesapp.db.MovieDetailsDao
import com.moviesapp.model.MovieDetails
import com.moviesapp.network.base.NetworkResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OMDBRepository(private val movieDetailsDao: MovieDetailsDao) : BaseRepository() {

    private val TAG = OMDBRepository::class.java.simpleName
    private val movieDetails = MutableLiveData<NetworkResponse<MovieDetails>>()

    val allFavMovieList: Flow<List<MovieDetails>> = movieDetailsDao.getMovieDetailsList()
    val allFavMovieListOnDemand: List<MovieDetails> = movieDetailsDao.getMovieDetailsListOnDemand()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insertMovie(movieDetails: MovieDetails) {
        movieDetailsDao.insertMovie(movieDetails)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun deleteMovie(movieDetails: MovieDetails) {
        movieDetails.title?.let { movieDetailsDao.deleteMovie(it) }
    }

    fun getMovieDetails(movie: String): MutableLiveData<NetworkResponse<MovieDetails>> {
        omdbApiServices.getMovieDetails(movie).enqueue(object : Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                if (response.isSuccessful) {
                    Log.d(
                        TAG,
                        "Retrofit call successful with response " + response.body().toString()
                    )
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