package com.example.mvc_project.presentation.ui.main.mapper

import com.example.mvc_project.presentation.ui.model.MovieDetailUiState
import com.example.mvc_project.presentation.ui.model.MovieUiState

fun MovieUiState.toMovieDetailUiState() = MovieDetailUiState(
    moviePoster = this.moviePoster.orEmpty(),
    movieName = this.movieName.orEmpty(),
    movieRunningTime = this.movieRunningTime.orEmpty(),
    movieGrade = this.movieGrade.orEmpty(),
    actorName = this.actorName.orEmpty(),
    director = this.director.orEmpty(),
    plotText = this.plotText.orEmpty(),
    prodYear = this.prodYear.orEmpty()
)