package com.joohyeong.architecture_pattern_study.data.mapper

import com.joohyeong.architecture_pattern_study.data.response.MovieEntity
import com.joohyeong.architecture_pattern_study.model.MovieDetail

fun MovieEntity.toMovieDetail(): MovieDetail = MovieDetail(
    id = id,
    title = title,
    runtime = runtime,
    rating = rating,
    posterUrl = posters.split("|").firstOrNull().orEmpty(),
    releaseDate = releaseDate,
    plot = plots.plot.firstOrNull()?.plotText.orEmpty(),
    actors = actors.actor.map { it.actorNm },
    directors = directors.director.map { it.directorNm }
)