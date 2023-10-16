package com.example.movieapplication.ui.popular_movie


import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movieapplication.data.api.LAST_PAGE
import com.example.movieapplication.data.api.TheMovieDBInterface
import com.example.movieapplication.data.repository.network.MovieDataSourceFactory
import com.example.movieapplication.data.repository.network.NetworkState
import com.example.movieapplication.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository(private val apiService: TheMovieDBInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(LAST_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState>{
        return moviesDataSourceFactory.moviesLiveDataSource.switchMap {
            it.networkState
        }
    }

}