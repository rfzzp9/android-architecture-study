package com.example.mvc_project.model.usecase

import com.example.mvc_project.model.repository.MovieListRemoteRepository
import com.example.mvc_project.presenter.model.MovieUiState
import com.example.mvc_project.presenter.mapper.toUiModel

class GetMovieListUseCase(
    private val movieListRemoteRepository: MovieListRemoteRepository
) {
    suspend fun execute(): List<MovieUiState> {
        val response = movieListRemoteRepository.fetchMovieList()
        return response.data.firstOrNull()?.result?.map { it.toUiModel() } ?: emptyList()
    }
}