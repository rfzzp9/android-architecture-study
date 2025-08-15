package com.example.mvc_project.presentation.mapper

import com.example.mvc_project.presentation.MovieUiState
import com.example.mvc_project.data.dto.MovieData

fun MovieData.toUiModel() = MovieUiState(
    movieName = title,
    moviePoster = posterUrl,
    movieRunningTime = runtime,
    movieGrade = rating,
    actorName = actorList.actorList.first().actorName,
    director = directorList.directorList.first().directorName,
    plotText = plotList.plotList.first().plotText,
    prodYear = prodYear
)