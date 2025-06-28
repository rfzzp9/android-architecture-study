package com.example.mvc_project.model.dataSource

import com.example.mvc_project.model.data.ResponseDTO
import com.example.mvc_project.model.network.ApiService

class MovieListRemoteDataSource(
    private val api: ApiService
) {
    suspend fun fetchMovieList(): ResponseDTO {
        return try {
            val response = api.fetchMovieList()
            response.body() ?: emptyResponseDTO()
        } catch (e: Exception) {
            emptyResponseDTO()
        }
    }

    private fun emptyResponseDTO() = ResponseDTO(data = emptyList())
}