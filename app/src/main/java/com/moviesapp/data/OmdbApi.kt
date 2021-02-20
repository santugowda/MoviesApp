package com.moviesapp.data

import com.moviesapp.data.model.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OmdbApi {

    @GET("trending/{trend_type}/{time}")
    suspend fun getTrendingMovies(
        @Path("trend_type") trendType : String,
        @Path("time") time : String,
        @Query("page") page : Int
    ): Response<Result>


}