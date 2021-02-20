package com.moviesapp.model

import com.google.gson.annotations.SerializedName

data class Search(
    @SerializedName("imdbID")
    var id: String,
    @SerializedName("Title")
    var title: String,
    @SerializedName("Year")
    var year: String,
    @SerializedName("Type")
    var type: String,
    @SerializedName("Poster")
    var poster: String?,
    var createdAt: Long
){
    constructor() : this("","","","","",0L)
}