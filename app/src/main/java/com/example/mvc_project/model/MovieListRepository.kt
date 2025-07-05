package com.example.mvc_project.model

import com.example.mvc_project.model.dto.ResponseDTO

class MovieListRepository(
    private val movieListRemoteDataSource: MovieListDataSource
) {
    suspend fun fetchMovieList(): ResponseDTO {
        return movieListRemoteDataSource.fetchMovieList()
    }
}