package com.example.mvc_project.controller

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvc_project.R
import com.example.mvc_project.databinding.ItemBinding

class MovieAdapter(private val onMovieItemClick: ((MovieUiState) -> Unit)?) :
    ListAdapter<MovieUiState, MovieAdapter.MovieViewHolder>(
        MovieDiffUtil()
    ) {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): MovieAdapter.MovieViewHolder {
        val binding: ItemBinding =
            ItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return MovieViewHolder(binding, onMovieItemClick)
    }

    override fun onBindViewHolder(viewHolder: MovieAdapter.MovieViewHolder, position: Int) {
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
                        onMovieItemClick?.invoke(movieItem ?: MovieUiState())
                    }
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(data: MovieUiState) {
            clickedItem = data
            with(binding) {
                tvMovieName.text = data.movieName.takeIf { !it.isNullOrBlank() } ?: "제목 없음"
                tvMovieRunningTime.text = data.movieRunningTime?.let { "${it}분" } ?: "시간 정보 없음"
                tvMovieGrade.text = data.movieGrade.takeIf { !it.isNullOrBlank() } ?: "등급 정보 없음"
                updateMoviePoster(data.moviePoster)
            }
        }

        private fun updateMoviePoster(moviePosterUrl: String) {
            Glide.with(itemView.context)
                .load(moviePosterUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.warning)
                .into(binding.ivMoviePoster)
        }
    }

    class MovieDiffUtil : DiffUtil.ItemCallback<MovieUiState>() {
        override fun areItemsTheSame(oldItem: MovieUiState, newItem: MovieUiState): Boolean {
            return newItem.movieName == oldItem.movieName && newItem.prodYear == oldItem.prodYear
        }

        override fun areContentsTheSame(oldItem: MovieUiState, newItem: MovieUiState): Boolean {
            return oldItem == newItem
        }
    }

}