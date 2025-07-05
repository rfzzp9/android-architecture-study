package com.example.mvc_project.presentation.sideeffect

import com.example.mvc_project.domain.model.MovieUiState

sealed class MainSideEffect {
    data class ShowMovieDetailDialog(val movie: MovieUiState) : MainSideEffect()
    data class ShowErrorMessage(val message: String) : MainSideEffect()
}