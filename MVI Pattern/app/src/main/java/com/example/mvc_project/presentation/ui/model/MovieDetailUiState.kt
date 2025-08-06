package com.example.mvc_project.presentation.ui.model

data class MovieDetailUiState(
    val moviePoster: String = "",
    val movieName: String = "",
    val movieRunningTime: String = "",
    val movieGrade: String = "",
    val actorName: String = "",
    val director: String = "",
    val plotText: String = "",
    val prodYear: String = "",
)