package com.example.mvc_project.presentation.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvc_project.R
import com.example.mvc_project.databinding.ItemBinding
import com.example.mvc_project.domain.model.MovieUiState

class MovieAdapter(
    private val onMovieItemClick: (MovieUiState) -> Unit
) : ListAdapter<MovieUiState, MovieAdapter.MovieViewHolder>(MovieDiffUtil()) {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding: ItemBinding =
            ItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return MovieViewHolder(binding, onMovieItemClick)
    }

    override fun onBindViewHolder(viewHolder: MovieViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    class MovieViewHolder(
        private val binding: ItemBinding,
        private val onMovieItemClick: ((MovieUiState) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {
        private var clickedItem: MovieUiState? = null

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    clickedItem?.let { movieItem ->
                        onMovieItemClick?.invoke(movieItem)
                    }
                }
            }
        }

        fun bind(data: MovieUiState) {
            clickedItem = data
            with(binding) {
                tvMovieName.text = data.movieName.takeIf { !it.isNullOrBlank() }
                    ?: R.string.no_title.toString()

                tvMovieRunningTime.text = data.movieRunningTime?.let { "${it}분" }
                    ?: R.string.no_runtime_info.toString()

                tvMovieGrade.text = data.movieGrade.takeIf { !it.isNullOrBlank() }
                    ?: R.string.no_grade_info.toString()

                loadMoviePoster(data.moviePoster)
            }
        }

        private fun loadMoviePoster(posterUrl: String?) {
            Glide.with(itemView.context)
                .load(posterUrl)
                .placeholder(R.drawable.placeholder_movie_poster)
                .error(R.drawable.error_movie_poster)
                .into(binding.ivMoviePoster)
        }
    }

    class MovieDiffUtil : DiffUtil.ItemCallback<MovieUiState>() {
        override fun areItemsTheSame(oldItem: MovieUiState, newItem: MovieUiState): Boolean {
            return newItem.movieId == oldItem.movieId
        }

        override fun areContentsTheSame(oldItem: MovieUiState, newItem: MovieUiState): Boolean {
            return oldItem == newItem
        }
    }
}