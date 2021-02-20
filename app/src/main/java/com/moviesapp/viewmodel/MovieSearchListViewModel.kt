package com.moviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.moviesapp.model.Search
import com.moviesapp.repository.MovieSearchDataSource
import com.moviesapp.repository.MovieSearchDataSourceFactory
import java.util.concurrent.Executors

class MovieSearchListViewModel : ViewModel() {

    lateinit var dataSource: MutableLiveData<MovieSearchDataSource>
    lateinit var searchLiveData: LiveData<PagedList<Search>>

    init {
        initUsersListFactory()
    }

    private fun initUsersListFactory() {
        val movieListDataSourceFactory =  MovieSearchDataSourceFactory()
        dataSource = movieListDataSourceFactory.liveData

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(MovieSearchDataSource.PAGE_SIZE)
            .setPageSize(MovieSearchDataSource.PAGE_SIZE)
            .setPrefetchDistance(2)
            .build()

        val executor = Executors.newFixedThreadPool(5)

        searchLiveData = LivePagedListBuilder(movieListDataSourceFactory, config)
            .setFetchExecutor(executor)
            .build()
    }

}