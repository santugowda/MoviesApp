package com.moviesapp.data.api

import com.moviesapp.data.OmdbApi
import com.moviesapp.data.base.Resource
import com.moviesapp.data.model.MovieSearch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OmdbApiClientImpl(private val omdbApi: OmdbApi) : OmdbApiClient {

    override suspend fun getSearchedMoviesList(
        movieName: String,
        page: Int
    ): Resource<MovieSearch> =
        withContext(
            Dispatchers.IO
        ) {
            try {
                val response = omdbApi.getSearchedMovies(movieName, page)
                if (response.isSuccessful) {
                    Resource.success(response.body())

                } else {
                    Resource.error(response.message())
                }
            } catch (ex: Throwable) {
                Resource.error("${ex.message}")
            }
        }
}