package com.moviesapp.network

import com.moviesapp.utils.MovieConstants.MOVIES_BASE_URL
import com.moviesapp.utils.MovieConstants.TIME_OUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private val TAG = RetrofitClient::class.java.simpleName

    private fun createRetrofitClient(): Retrofit {
        val okHttpClient = OkHttpClient.Builder().readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()


        return Retrofit.Builder()
            .baseUrl(MOVIES_BASE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @JvmStatic
    fun<S> getClient(serviceClass : Class<S>) : S {
        return createRetrofitClient().create(serviceClass)
    }
}
