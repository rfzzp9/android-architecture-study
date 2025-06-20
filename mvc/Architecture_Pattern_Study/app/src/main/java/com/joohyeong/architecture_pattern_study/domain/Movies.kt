package com.joohyeong.architecture_pattern_study.domain

class Movies(
    private val values: List<Movie>
) {
    fun getMovies(): List<Movie> {
        return values
    }
}