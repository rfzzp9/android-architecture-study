package com.example.mvc_project.presentation.ui.detail

import com.example.mvc_project.presentation.ui.model.MovieDetailUiState

data class MovieDetailViewState(
    val isLoading: Boolean = false,
    val movieDetail: MovieDetailUiState = MovieDetailUiState(),
    val error: String? = null
)