package com.example.movieapplication.data.vo


import com.google.gson.annotations.SerializedName

data class MovieDetails(
    @SerializedName("description")
    val description: String,
    @SerializedName("filmLength")
    val filmLength: Int,
    @SerializedName("kinopoiskId")
    val kinopoiskId: Int,
    @SerializedName("nameOriginal")
    val nameOriginal: String,
    @SerializedName("nameRu")
    val nameRu: String,
    @SerializedName("posterUrl")
    val posterUrl: String,
    @SerializedName("ratingKinopoisk")
    val ratingKinopoisk: Double,
    @SerializedName("slogan")
    val slogan: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("ratingAgeLimits")
    val age: String?,
    @SerializedName("countries")
    val countries: List<Country>,
    @SerializedName("genres")
    val genres: List<Genre>
)