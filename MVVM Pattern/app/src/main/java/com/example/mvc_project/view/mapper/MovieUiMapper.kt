package com.example.mvc_project.view.mapper

import com.example.mvc_project.view.MovieUiState
import com.example.mvc_project.model.dto.MovieDataDTO

fun MovieDataDTO.toUiModel() = MovieUiState(
    movieName = title,
    moviePoster = posterUrl,
    movieRunningTime = runtime,
    movieGrade = rating,
    actorName = actorList.actorList.first().actorName,
    director = directorList.directorList.first().directorName,
    plotText = plotList.plotList.first().plotText,
    prodYear = prodYear
)