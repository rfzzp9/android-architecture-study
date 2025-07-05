package com.example.mvc_project.data

import com.example.mvc_project.data.dto.ResponseDTO

class MovieListRepository(
    private val movieListRemoteDataSource: MovieListDataSource
) {
    suspend fun fetchMovieList(): ResponseDTO {
        return movieListRemoteDataSource.fetchMovieList()
    }
}