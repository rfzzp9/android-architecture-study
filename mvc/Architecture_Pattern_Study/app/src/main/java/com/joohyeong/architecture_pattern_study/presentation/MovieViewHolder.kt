package com.joohyeong.architecture_pattern_study.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.joohyeong.architecture_pattern_study.R
import com.joohyeong.architecture_pattern_study.databinding.ItemMovieBinding
import com.joohyeong.architecture_pattern_study.domain.Movie

class MovieViewHolder(
    parent: ViewGroup,
    private val onMovieClick: (position: Int) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_movie, parent, false)
) {
    private val binding = ItemMovieBinding.bind(itemView)

    init {
        binding.root.setOnClickListener {
            onMovieClick(adapterPosition)
        }
    }

    fun bind(movie: Movie) {
        with(binding) {
            textMovieTitle.text = movie.title
            textMovieAudienceRating.text = movie.audienceRating
            textMovieRuntime.text = movie.runtime
            Glide.with(itemView)
                .load(movie.posterUrl)
                .apply(RequestOptions.centerCropTransform())
                .into(imageMoviePoster)
        }
    }
}