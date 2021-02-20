package com.moviesapp.repository

import com.moviesapp.network.OmdbApi
import com.moviesapp.network.RetrofitClient

open class BaseRepository {

    open val omdbApiServices = RetrofitClient.getClient(OmdbApi::class.java)
}