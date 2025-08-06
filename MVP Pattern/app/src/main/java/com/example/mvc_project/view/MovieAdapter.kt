package com.example.mvc_project.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvc_project.databinding.ItemBinding
import com.example.mvc_project.presenter.model.MovieUiState

class MovieAdapter(private val onMovieListFinishListener: ((MovieUiState) -> Unit)?) :
    ListAdapter<MovieUiState, MovieAdapter.MovieViewHolder>(
        MovieDiffUtil()
    ) {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding: ItemBinding =
            ItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return MovieViewHolder(binding, onMovieListFinishListener)
    }

    override fun onBindViewHolder(viewHolder: MovieViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    class MovieViewHolder(
        private val binding: ItemBinding,
        private val onMovieListFinishListener: ((MovieUiState) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {
        private var clickedItem: MovieUiState? = null

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    clickedItem?.let { movieItem ->
                        onMovieListFinishListener?.invoke(movieItem ?: MovieUiState())
                    }
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(data: MovieUiState) {
            clickedItem = data

            updateMoviePoster(data.moviePoster.toString())
            binding.tvMovieName.text = "${data.movieName}"
            binding.tvMovieRunningTime.text = "${data.movieRunningTime}"
            binding.tvMovieGrade.text = "${data.movieGrade}"
        }

        private fun updateMoviePoster(moviePosterUrl: String) {
            Glide.with(itemView.context)
                .load(moviePosterUrl)
                .into(binding.ivMoviePoster)
        }
    }

    class MovieDiffUtil : DiffUtil.ItemCallback<MovieUiState>() {
        override fun areItemsTheSame(oldItem: MovieUiState, newItem: MovieUiState): Boolean {
            return newItem.moviePoster == oldItem.moviePoster
        }

        override fun areContentsTheSame(oldItem: MovieUiState, newItem: MovieUiState): Boolean {
            return oldItem == newItem
        }
    }

}