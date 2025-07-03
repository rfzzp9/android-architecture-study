package com.joohyeong.architecture_pattern_study.presentation.main

sealed interface MainIntent {
    object LoadMovies : MainIntent
}