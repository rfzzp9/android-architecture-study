package com.example.mvc_project.controller.mapper

import com.example.mvc_project.controller.MovieUiState
import com.example.mvc_project.model.data.MovieDataDTO

fun MovieDataDTO.toUiModel() = MovieUiState(
    movieName = title,
    moviePoster = kmdbUrl,
    movieRunningTime = runtime,
    movieGrade = rating,
    actorName = actors.actor.first().actorNm,
    director = directors.director.first().directorNm,
    plotText = plots.plot.first().plotText,
    prodYear = prodYear
)