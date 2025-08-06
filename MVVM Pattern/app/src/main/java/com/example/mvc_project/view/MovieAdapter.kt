package com.example.mvc_project.view

import com.example.mvc_project.R
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvc_project.databinding.ItemBinding

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
                        onMovieItemClick?.invoke(movieItem ?: MovieUiState())
                    }
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(data: MovieUiState) {
            clickedItem = data
            with(binding) {
                tvMovieName.text = data.movieName.takeIf { !it.isNullOrBlank() }      // 영화 제목
                    ?: "제목 없음"

                tvMovieRunningTime.text = data.movieRunningTime?.let { "${it}분" }    // 러닝 타임
                    ?: "시간 정보 없음"

                tvMovieGrade.text = data.movieGrade.takeIf { !it.isNullOrBlank() }   // 관람 등급
                    ?: "등급 정보 없음"

                // 포스터 이미지 로딩
                loadMoviePoster(data.moviePoster)
            }
        }

        private fun loadMoviePoster(posterUrl: String?) {
            Glide.with(itemView.context)
                .load(posterUrl)
                .placeholder(R.drawable.placeholder_movie_poster)           // 로딩 중 이미지 표시
                .error(R.drawable.error_movie_poster)                       // 에러 시 이미지 표시
                .into(binding.ivMoviePoster)
        }
    }

    class MovieDiffUtil : DiffUtil.ItemCallback<MovieUiState>() {
        override fun areItemsTheSame(oldItem: MovieUiState, newItem: MovieUiState): Boolean {
            return newItem.movieName == oldItem.movieName && newItem.prodYear == oldItem.prodYear    // 영화명과 개봉연도로 같은 아이템인지 판단
        }

        override fun areContentsTheSame(oldItem: MovieUiState, newItem: MovieUiState): Boolean {
            return oldItem == newItem
        }
    }

}