package com.example.mvc_project.model.repository

import com.example.mvc_project.model.data.ResponseDTO
import com.example.mvc_project.model.dataSource.MovieListRemoteDataSource

class MovieListRemoteRepository(
    private val movieListRemoteDataSource: MovieListRemoteDataSource
) {
    suspend fun fetchMovieList(): ResponseDTO {
        return movieListRemoteDataSource.fetchMovieList()
    }
}