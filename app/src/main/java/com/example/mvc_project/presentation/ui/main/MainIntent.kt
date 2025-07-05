package com.example.mvc_project.presentation.ui.main

import com.example.mvc_project.domain.model.MovieUiState

sealed class MainIntent {
    object LoadMovieList : MainIntent()
    data class ShowMovieDetail(val movie: MovieUiState) : MainIntent()
    object RetryLoadMovieList : MainIntent()
}