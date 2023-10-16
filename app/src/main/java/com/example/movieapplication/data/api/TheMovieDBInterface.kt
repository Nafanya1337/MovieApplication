package com.example.movieapplication.data.api

import com.example.movieapplication.data.vo.MovieDetails
import com.example.movieapplication.data.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {

    // https://kinopoiskapiunofficial.tech/api/v2.2/films?ratingFrom=0&ratingTo=10&yearFrom=1000&yearTo=3000


    @GET("films")
    fun getPopularMovie(
        @Query("ratingFrom") ratingFrom: Int = 0,
        @Query("ratingTo") ratingTo: Int = 10,
        @Query("yearFrom") yearFrom: Int = 1000,
        @Query("yearTo") yearTo: Int = 3000,
        @Query("page") page: Int = 1
    ) : Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>
}