package com.example.movieapplication.ui.popular_movie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapplication.R
import com.example.movieapplication.data.repository.network.NetworkState
import com.example.movieapplication.data.repository.network.Status
import com.example.movieapplication.data.vo.Movie
import com.example.movieapplication.ui.single_movie_details.SingleMovie

class PopularMoviePagedListAdapter(public val context: Context) : PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2
    private var networkState: NetworkState? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(getItem(position),context)
        }
        else {
            //(holder as MovieItemViewHolder.NetworkStateItemViewHolder).bind(networkState)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            MOVIE_VIEW_TYPE
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View
        if (viewType == MOVIE_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.movie_list_item, parent, false)
            return MovieItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            return MovieItemViewHolder.NetworkStateItemViewHolder(view)
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.kinopoiskId == newItem.kinopoiskId
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    class MovieItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie?, context: Context) {
            val text = if (movie?.nameRu == "") {
                movie.nameOriginal
            } else
                movie?.nameRu
            itemView.findViewById<TextView>(R.id.small_movie_title).text = text
            itemView.findViewById<TextView>(R.id.small_movie_rating).text =
                movie?.ratingKinopoisk.toString()
            itemView.findViewById<TextView>(R.id.small_movie_release_date).text =
                movie?.year.toString()

            val moviePosterURL = movie?.posterUrlPreview
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(itemView.findViewById(R.id.small_movie_poster))

            itemView.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, SingleMovie::class.java)
                intent.putExtra("id", movie?.kinopoiskId)
                context.startActivity(intent)
            })

        }

        class NetworkStateItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {
            fun bind(networkState: NetworkState?) {
                if (networkState != null && networkState.status == Status.RUNNING) {
                    itemView.findViewById<ProgressBar>(R.id.progress_bar_popular).visibility = View.VISIBLE;
                } else {
                    itemView.findViewById<ProgressBar>(R.id.progress_bar_popular).visibility = View.GONE;
                }

                if (networkState != null && networkState.status == Status.FAILED) {
                    itemView.findViewById<TextView>(R.id.text_error_popular).visibility = View.VISIBLE;
                    itemView.findViewById<TextView>(R.id.text_error_popular).setText(networkState.msg);
                } else {
                    itemView.findViewById<TextView>(R.id.text_error_popular).visibility = View.GONE;
                }

            }
        }

    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }
}