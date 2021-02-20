package com.moviesapp.repository

import androidx.paging.PageKeyedDataSource
import com.moviesapp.model.MovieSearch
import com.moviesapp.model.Search
import com.moviesapp.network.OmdbApi
import com.moviesapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieSearchDataSource : PageKeyedDataSource<Int, Search>() {

    val omdbApiServices = RetrofitClient.getClient(OmdbApi::class.java)
    private val FIRST_PAGE = 1

    companion object{
        const val PAGE_SIZE = 7
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Search>
    ) {
        omdbApiServices.getSearchedMovies("star", FIRST_PAGE).enqueue(object : Callback<MovieSearch> {
            override fun onFailure(call: Call<MovieSearch>, t: Throwable) {

            }

            override fun onResponse(call: Call<MovieSearch>, response: Response<MovieSearch>) {
                if(response.isSuccessful) {
                    response.body()?.search?.let { callback.onResult(it, null, 2) }
                }
            }

        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Search>) {
        omdbApiServices.getSearchedMovies("star", params.key).enqueue(object : Callback<MovieSearch> {
            override fun onFailure(call: Call<MovieSearch>, t: Throwable) {
            }

            override fun onResponse(call: Call<MovieSearch>, response: Response<MovieSearch>) {
                if(response.isSuccessful) {
                    response.body()?.search?.let { callback.onResult(it, params.key + 1) }
                }
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Search>) {

    }

}