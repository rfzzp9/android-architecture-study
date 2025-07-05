package com.example.mvc_project.data

import com.example.mvc_project.data.api.ApiService
import com.example.mvc_project.data.dto.Response

class MovieListDataSource(
    private val api: ApiService
) {
    suspend fun fetchMovieList(): Response {
        return try {
            val response = api.fetchMovieList()
            response.body() ?: emptyResponseDTO()
        } catch (e: Exception) {
            emptyResponseDTO()
        }
    }

    private fun emptyResponseDTO() = Response(dataList = emptyList())
}