package com.moviesapp.network

import com.moviesapp.model.MovieDetails
import com.moviesapp.model.MovieSearch
import com.moviesapp.utils.MovieConstants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {

    @GET("?apiKey=" + MovieConstants.API_KEY)
    fun getSearchedMovies(
        @Query("s") search : String?,
        @Query("page") page : Int
    ): Call<MovieSearch>


    @GET("?apiKey=" + MovieConstants.API_KEY)
    fun getMovieDetails(
        @Query("t") movie : String
    ): Call<MovieDetails>
}