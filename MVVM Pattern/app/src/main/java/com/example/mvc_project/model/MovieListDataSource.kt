package com.example.mvc_project.model

import com.example.mvc_project.model.dto.ResponseDTO
import com.example.mvc_project.model.network.ApiService


class MovieListDataSource(
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

    private fun emptyResponseDTO() = ResponseDTO(dataList = emptyList())
}