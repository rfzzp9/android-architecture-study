package com.joohyeong.architecture_pattern_study.presentation.moviedetail

sealed interface MovieDetailIntent {
    data class LoadMovieDetail(val movieId: String) : MovieDetailIntent
}