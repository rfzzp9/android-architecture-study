package com.example.mvc_project.controller

data class MovieUiState(
    val moviePoster: String? = null,
    val movieName: String? = null,
    val movieRunningTime: String? = null,
    val movieGrade: String? = null,
    val actorName: String? = null,
    val director: String? = null,
    val plotText: String? = null,
    val prodYear: String? = null
)
