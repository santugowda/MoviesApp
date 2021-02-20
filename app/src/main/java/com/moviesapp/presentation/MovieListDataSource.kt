package com.moviesapp.presentation

import androidx.paging.PageKeyedDataSource
import com.moviesapp.data.api.OmdbApiClient
import com.moviesapp.data.model.MovieSearch

class MovieListDataSource(private val omdbApiClient: OmdbApiClient): PageKeyedDataSource<Int, MovieSearch >() {



    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieSearch>
    ) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieSearch>) {
        TODO("Not yet implemented")
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieSearch>) {
        TODO("Not yet implemented")
    }
}