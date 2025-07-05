package com.example.mvc_project.data

import com.example.mvc_project.data.dto.Response

class MovieListRepository(
    private val movieListRemoteDataSource: MovieListDataSource
) {
    suspend fun fetchMovieList(): Response {
        return movieListRemoteDataSource.fetchMovieList()
    }
}