package com.moviesapp.data.api

import com.moviesapp.data.base.Resource
import com.moviesapp.data.model.MovieSearch

interface OmdbApiClient {

    suspend fun getSearchedMoviesList(movieName : String, page: Int): Resource<MovieSearch>
}