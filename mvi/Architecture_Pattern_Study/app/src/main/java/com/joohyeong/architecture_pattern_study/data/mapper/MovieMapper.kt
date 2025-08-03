package com.joohyeong.architecture_pattern_study.data.mapper

import com.joohyeong.architecture_pattern_study.data.response.MovieEntity
import com.joohyeong.architecture_pattern_study.model.Movie

fun MovieEntity.toMovie(): Movie = Movie(
    id = id,
    title = title,
    posterUrl = posters.split("|").firstOrNull().orEmpty(),
    audienceRating = rating,
    runtime = runtime
)