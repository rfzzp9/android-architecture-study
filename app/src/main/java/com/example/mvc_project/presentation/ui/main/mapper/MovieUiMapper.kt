package com.example.mvc_project.presentation.ui.main.mapper

import com.example.mvc_project.domain.model.MovieUiState
import com.example.mvc_project.data.dto.MovieDataDTO

fun MovieDataDTO.toUiModel() = MovieUiState(
    movieId = movieId,
    movieName = title,
    moviePoster = posterUrl,
    movieRunningTime = runtime,
    movieGrade = rating,
    actorName = actorList.actorList.first().actorName,
    director = directorList.directorList.first().directorName,
    plotText = plotList.plotList.first().plotText,
    prodYear = prodYear
)