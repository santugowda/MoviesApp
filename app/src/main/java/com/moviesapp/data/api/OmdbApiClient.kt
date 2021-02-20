package com.moviesapp.data.api

import com.moviesapp.data.base.Resource
import com.moviesapp.data.model.Result

interface OmdbApiClient {

    suspend fun getTrendingMovies(trendType : String, time : String, page: Int): Resource<Result>
}