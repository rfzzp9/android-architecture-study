package com.example.mvc_project.controller.mapper

import com.example.mvc_project.controller.MovieDetailUiState
import com.example.mvc_project.controller.MovieUiState
import com.example.mvc_project.model.data.MovieDataDTO

fun MovieDataDTO.toUiModel() = MovieUiState(
    movieName = title,
    moviePoster = posters,
    movieRunningTime = runtime,
    movieGrade = rating,
    actorName = actors.actor.first().actorNm,
    director = directors.director.first().directorNm,
    plotText = plots.plot.first().plotText,
    prodYear = prodYear
)

fun MovieUiState.toMovieDetailModel() = MovieDetailUiState(
    movieName = movieName,
    moviePoster = moviePoster,
    movieRunningTime = movieRunningTime,
    movieGrade = movieGrade,
    actorName = actorName,
    director = director,
    plotText = plotText,
    prodYear = prodYear
)