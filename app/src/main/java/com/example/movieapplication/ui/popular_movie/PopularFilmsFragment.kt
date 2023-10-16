package com.example.movieapplication.ui.popular_movie

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapplication.data.api.TheMovieDBClient
import com.example.movieapplication.data.api.TheMovieDBInterface
import com.example.movieapplication.data.repository.network.NetworkState
import com.example.movieapplication.databinding.FragmentPopularFilmsBinding
import com.example.movieapplication.ui.MainActivityViewModel


class PopularFilmsFragment : Fragment() {

    private lateinit var viewModel: MainActivityViewModel

    lateinit var movieRepository: MoviePagedListRepository


    private lateinit var binding: FragmentPopularFilmsBinding

    private lateinit var context: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiService: TheMovieDBInterface = TheMovieDBClient.getPopularClient(page = 1)
        movieRepository = MoviePagedListRepository(apiService)
        viewModel = getViewModel()
        val movieAdapter = PopularMoviePagedListAdapter(context)
        val gridLayoutManager = GridLayoutManager(context, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return 1
                else return 3
            }
        }

        binding.movieList.layoutManager = gridLayoutManager
        binding.movieList.setHasFixedSize(true)
        binding.movieList.adapter = movieAdapter

        viewModel.moviePagedList.observe(requireActivity(), Observer {
            movieAdapter.submitList(it)
        })


        viewModel.networkState.observe(requireActivity(), Observer {
            binding.progressBarPopular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING)
                    View.VISIBLE
                else
                    View.GONE

            binding.textErrorPopular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR)
                    View.VISIBLE
                else
                    View.GONE

            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })
    }


    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainActivityViewModel(movieRepository) as T
            }

        })[MainActivityViewModel::class.java]
    }
}