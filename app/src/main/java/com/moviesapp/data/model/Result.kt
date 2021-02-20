package com.moviesapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class Result(
    @SerializedName("page")
    @Expose
    private val page: Int? = null,

    @SerializedName("total_results")
    @Expose
    private val totalResults: Int? = null,

    @SerializedName("total_pages")
    @Expose
    private val totalPages: Int? = null,

    @SerializedName("results")
    @Expose
    private val results: ArrayList<Movie>? = null
)