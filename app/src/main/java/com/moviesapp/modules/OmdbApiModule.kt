package com.moviesapp.modules

import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.moviesapp.data.OmdbApi
import com.moviesapp.data.api.OmdbApiClient
import com.moviesapp.data.api.OmdbApiClientImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val TIME_OUT: Long = 30
const val BASE_URL = "https://api.themoviedb.org/3/"

val omdbApiModule = module {
    factory { provideHttpLoggingInterceptor() }
    factory { provideGson() }
    factory { provideOkHttpClient(get()) }
    factory { provideOmdbApi(get()) }
    single { provideRetrofit(get(), get()) }
}

val omdbApiClientModule = module {
    single<OmdbApiClient> { OmdbApiClientImpl(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

fun provideOmdbApi(retrofit: Retrofit): OmdbApi = retrofit.create(OmdbApi::class.java)

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.d("OMDB API", message)
        }
    })
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}

fun provideGson(): Gson {
    return GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        .setLenient()
        .create()
}

fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val builder = OkHttpClient.Builder()
    builder
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            val actualUrl = chain.request().url

            val url = actualUrl.newBuilder().addQueryParameter("api_key", "add the key").build()
            requestBuilder.url(url)
            chain.proceed(requestBuilder.build())
        }
    builder.addInterceptor(httpLoggingInterceptor)
    return builder.build()
}