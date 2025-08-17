package com.example.mvc_yongho.presentation.model

import com.example.mvc_yongho.domain.model.Movie

sealed interface MovieUiState {
    data object Empty : MovieUiState
    data object Loading : MovieUiState
    data class HasMovies(
        val movies: List<Movie>
    ) : MovieUiState
    data class Error(
        val message :String
    ) : MovieUiState
}
