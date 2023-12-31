package com.example.movieapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.movieapplication.data.repository.network.NetworkState
import com.example.movieapplication.data.vo.Movie
import com.example.movieapplication.ui.popular_movie.MoviePagedListRepository
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(
    private val movieRepository: MoviePagedListRepository,
) : ViewModel() {


    private val compositeDisposable = CompositeDisposable()

    val moviePagedList: LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }


    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }


    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}