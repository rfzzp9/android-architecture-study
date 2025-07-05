package com.example.mvc_project.presentation.ui.detail

import com.example.mvc_project.domain.model.MovieUiState

sealed class MovieDetailIntent {
    data class LoadMovieDetail(val movie: MovieUiState) : MovieDetailIntent()
    object DismissDialog : MovieDetailIntent()
}