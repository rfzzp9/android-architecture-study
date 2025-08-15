package com.example.mvc_project.data

import com.example.mvc_project.data.dto.MovieResponse
import com.example.mvc_project.data.MovieListRepository
import jakarta.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieListRemoteDataSource: MovieListDataSource
) : MovieListRepository {
    override suspend fun fetchMovieList(): MovieResponse {
        return movieListRemoteDataSource.fetchMovieList()
    }
}