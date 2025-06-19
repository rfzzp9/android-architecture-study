package com.example.mvc_yongho.model.repository

import com.example.mvc_yongho.model.data.MovieInfo

interface MovieRepository {
    suspend fun searchMovies(title: String): Result<List<MovieInfo>>
}
