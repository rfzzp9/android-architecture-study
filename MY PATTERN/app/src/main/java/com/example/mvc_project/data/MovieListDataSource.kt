package com.example.mvc_project.data

import com.example.mvc_project.data.dto.MovieResponse
import com.example.mvc_project.data.network.ApiService
import javax.inject.Inject


class MovieListDataSource @Inject constructor(
    private val api: ApiService
) {
    suspend fun fetchMovieList(): MovieResponse {
        return try {
            val response = api.fetchMovieList()
            response.body() ?: emptyResponse()
        } catch (e: Exception) {
            emptyResponse()
        }
    }

    private fun emptyResponse() = MovieResponse(dataList = emptyList())
}