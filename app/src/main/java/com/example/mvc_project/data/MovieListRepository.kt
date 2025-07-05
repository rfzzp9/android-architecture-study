package com.example.mvc_project.data

import com.example.mvc_project.R
import com.example.mvc_project.data.dto.Response
import com.example.mvc_project.presentation.ui.model.MovieDetailUiState
import com.example.mvc_project.presentation.ui.model.MovieUiState
import com.example.mvc_project.presentation.ui.model.isValid
import com.example.mvc_project.presentation.ui.main.mapper.toMovieDetailUiState

class MovieListRepository(
    private val movieListRemoteDataSource: MovieListDataSource
) {
    suspend fun fetchMovieList(): Response {
        return movieListRemoteDataSource.fetchMovieList()
    }

    suspend fun getMovieDetail(movie: MovieUiState): MovieDetailUiState {
        if (!movie.isValid()) {
            throw IllegalArgumentException(R.string.not_found_movieInfo.toString())
        }

        return movie.toMovieDetailUiState()
    }

}