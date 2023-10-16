package com.example.movieapplication.data.vo


import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("kinopoiskId")
    val kinopoiskId: Int,
    @SerializedName("nameOriginal")
    val nameOriginal: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("nameRu")
    val nameRu: String,
    @SerializedName("posterUrlPreview")
    val posterUrlPreview: String,
    @SerializedName("ratingKinopoisk")
    val ratingKinopoisk: Double
)