package com.moviesapp.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.moviesapp.model.Search

class MovieSearchDataSourceFactory(val movieSearch : String) : DataSource.Factory<Int, Search>() {

    val liveData: MutableLiveData<MovieSearchDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, Search> {
        val movieSearchDataSource = MovieSearchDataSource(movieSearch)
        liveData.postValue(movieSearchDataSource)
        return movieSearchDataSource
    }

}