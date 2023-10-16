package com.example.movieapplication.data.vo


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("items")
    val movieList: List<Movie>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalPages")
    val totalPages: Int
)