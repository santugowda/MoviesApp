package com.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieSearch(
    @SerializedName("Search")
    var search: List<Search>,

    @SerializedName("totalResults")
    var totalResults: String,

    @SerializedName("Response")
    var response: String
)