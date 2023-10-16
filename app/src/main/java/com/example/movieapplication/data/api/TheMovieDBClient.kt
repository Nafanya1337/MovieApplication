package com.example.movieapplication.data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


const val API_KEY = "e41fc368-426a-459e-b16e-82e2a231a322"
const val BASE_URL = "https://kinopoiskapiunofficial.tech"

const val POSTER_BASE_URL = "https://kinopoiskapiunofficial.tech/images/posters/kp/"
const val PREVIEW_POSTER_BASE_URL = "https://kinopoiskapiunofficial.tech/images/posters/kp_small/"

const val FIRST_PAGE = 1
const val LAST_PAGE = 5

object TheMovieDBClient {

    fun getMovieClient(filmId: String): TheMovieDBInterface {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestBuilder: Request.Builder = chain.request().newBuilder()
                val url = BASE_URL + "/api/v2.2/films/$filmId"
                requestBuilder.url(url)
                requestBuilder.header("accept", "application/json")
                requestBuilder.header("X-API-KEY", API_KEY)
                chain.proceed(requestBuilder.build())
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(TheMovieDBInterface::class.java)

        return retrofit
    }


    fun getPopularClient(
        ratingFrom: Int = 0,
        ratingTo: Int = 10,
        yearFrom: Int = 1000,
        yearTo: Int = 3000,
        page: Int
    ): TheMovieDBInterface {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestBuilder: Request.Builder = chain.request().newBuilder()
                val url =
                    "$BASE_URL/api/v2.2/films?ratingFrom=0&ratingTo=10&yearFrom=1000&yearTo=3000&page=$page"
                requestBuilder.url(url)
                requestBuilder.header("accept", "application/json")
                requestBuilder.header("X-API-KEY", API_KEY)
                chain.proceed(requestBuilder.build())
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(TheMovieDBInterface::class.java)

        return retrofit
    }

}