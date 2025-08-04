package com.example.mvc_yongho.presentation

sealed interface MainIntent {
    data class SearchMovie(val query: String): MainIntent
}
