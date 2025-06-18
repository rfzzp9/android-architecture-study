package com.joohyeong.architecture_pattern_study

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joohyeong.architecture_pattern_study.domain.Movies

class MovieAdapter(
    private val movies: Movies,
    private val onMovieClick: (movieId: String) -> Unit,
) : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            parent,
            onMovieClick =  {onMovieClick(movies.getMovies()[it].id)},
        )
    }

    override fun getItemCount(): Int = movies.getMovies().size

    override fun onBindViewHolder(viewHolder: MovieViewHolder, position: Int) {
        viewHolder.bind(movies.getMovies()[position])
    }
}