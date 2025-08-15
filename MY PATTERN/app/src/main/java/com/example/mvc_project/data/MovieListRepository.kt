package com.example.mvc_project.data

import com.example.mvc_project.data.dto.MovieResponse

interface MovieListRepository {
    suspend fun fetchMovieList(): MovieResponse
}