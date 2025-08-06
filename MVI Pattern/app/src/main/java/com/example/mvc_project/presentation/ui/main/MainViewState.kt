package com.example.mvc_project.presentation.ui.main

import com.example.mvc_project.presentation.ui.model.MovieUiState

data class MainViewState(
    val isLoading: Boolean = false,
    val movies: List<MovieUiState> = emptyList(),
    val error: String? = null
)