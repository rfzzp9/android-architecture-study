package com.example.mvc_yongho.presentation

sealed class MainIntent {
    data class SearchMovie(val query: String): MainIntent()
}
