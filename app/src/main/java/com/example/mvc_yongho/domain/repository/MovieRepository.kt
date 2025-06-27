package com.example.mvc_yongho.domain.repository

import com.example.mvc_yongho.domain.model.Movie

interface MovieRepository {
    suspend fun searchMovies(title: String): Result<List<Movie>>
}
