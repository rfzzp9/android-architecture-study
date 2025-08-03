package com.joohyeong.architecture_pattern_study.presentation.main

import com.joohyeong.architecture_pattern_study.model.Movie

sealed interface MainUIState {
    data class Success(val movies: List<Movie>) : MainUIState
    data object Loading : MainUIState
    data class Error(val exception: Throwable) : MainUIState
}