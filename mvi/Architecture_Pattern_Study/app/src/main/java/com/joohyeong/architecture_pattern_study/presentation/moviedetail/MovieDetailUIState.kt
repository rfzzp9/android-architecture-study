package com.joohyeong.architecture_pattern_study.presentation.moviedetail

import com.joohyeong.architecture_pattern_study.model.MovieDetail

sealed interface MovieDetailUIState {
    data class Success(val movieDetail: MovieDetail) : MovieDetailUIState
    data object Loading : MovieDetailUIState
    data class Error(val exception: Throwable) : MovieDetailUIState
}