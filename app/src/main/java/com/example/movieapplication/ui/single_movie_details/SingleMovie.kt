package com.example.movieapplication.ui.single_movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.movieapplication.data.api.TheMovieDBClient
import com.example.movieapplication.data.api.TheMovieDBInterface
import com.example.movieapplication.data.repository.network.NetworkState
import com.example.movieapplication.data.vo.MovieDetails
import com.example.movieapplication.databinding.ActivitySingleMovieBinding
import java.text.NumberFormat
import java.util.Locale

class SingleMovie : AppCompatActivity() {

    private lateinit var binding: ActivitySingleMovieBinding

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingleMovieBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val movieId: Int = intent.getIntExtra("id", 1)

        val apiService: TheMovieDBInterface = TheMovieDBClient.getMovieClient(movieId.toString())
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })


        viewModel.networkState.observe(this, Observer {
            binding.progressBar.visibility =
                if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.txtError.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    fun bindUI(it: MovieDetails) {
        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.getDefault())

        var ageLimit: String? = it.age
        if (ageLimit === null)
            ageLimit = ""
        else
            ageLimit = ageLimit.substringAfter("age") + "+"
        binding.movieTitle.text = "${it.nameOriginal} (${it?.nameRu ?: ""}) ${ageLimit}"
        binding.movieTagline.text = it.slogan
        binding.movieReleaseDate.text = it.year.toString()
        binding.movieRating.text = it.ratingKinopoisk.toString()
        binding.movieRuntime.text = it.filmLength.toString() + " минут"
        binding.movieOverview.text = it.description
        binding.movieCountries.text = it.countries.joinToString { it.country }
        binding.movieGenres.text = it.genres.joinToString { it.genre }


        val moviePosterURL = it.posterUrl
        Glide.with(this)
            .load(moviePosterURL)
            .into(binding.ivMoviePoster);
    }


    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SingleMovieViewModel(movieRepository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]

    }
}