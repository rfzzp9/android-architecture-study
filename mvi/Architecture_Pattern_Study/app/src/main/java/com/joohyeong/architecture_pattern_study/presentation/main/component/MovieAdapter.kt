package com.joohyeong.architecture_pattern_study.presentation.main.component

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joohyeong.architecture_pattern_study.model.Movie

class MovieAdapter(
    private val movies: List<Movie>,
    private val onMovieClick: (movieId: String) -> Unit,
) : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            parent,
            onMovieClick =  {onMovieClick(movies[it].id)},
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(viewHolder: MovieViewHolder, position: Int) {
        viewHolder.bind(movies[position])
    }
}