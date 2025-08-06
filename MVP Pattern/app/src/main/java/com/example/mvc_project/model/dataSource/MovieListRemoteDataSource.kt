package com.example.mvc_project.model.dataSource

import com.example.mvc_project.model.data.ResponseDTO
import com.example.mvc_project.model.network.ApiService

class MovieListRemoteDataSource(
    private val api: ApiService
) {
    suspend fun fetchMovieList(): ResponseDTO {
        val response = api.fetchMovieList()
        return (if (response.isSuccessful) {
            response.body() ?: ResponseDTO
        } else {
            ResponseDTO
        }) as ResponseDTO
    }
}