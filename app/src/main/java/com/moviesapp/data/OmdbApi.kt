package com.moviesapp.data

import com.moviesapp.data.model.MovieSearch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OmdbApi {

    @GET
    suspend fun getSearchedMovies(
        @Query("s") search : String,
        @Query("page_size") pageSize : Int
    ): Response<MovieSearch>


}