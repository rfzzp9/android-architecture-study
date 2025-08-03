package com.example.mvc_project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel : ViewModel() {

    private val _movieDetailUiState = MutableStateFlow(MovieDetailUiState())
    val movieDetailUiState: StateFlow<MovieDetailUiState> = _movieDetailUiState.asStateFlow()

    fun setMovieData(movie: MovieUiState) {
        viewModelScope.launch {
            try {
                if (isValidMovieData(movie)) {
                    _movieDetailUiState.value = MovieDetailUiState(
                        moviePoster = movie.moviePoster ?: "",
                        movieName = movie.movieName ?: "",
                        movieRunningTime = movie.movieRunningTime ?: "",
                        movieGrade = movie.movieGrade ?: "",
                        actorName = movie.actorName ?: "",
                        director = movie.director ?: "",
                        plotText = movie.plotText ?: "",
                        prodYear = movie.prodYear ?: ""
                    )
                } else {
                    _movieDetailUiState.value = MovieDetailUiState()  // 유효하지 않은 데이터일 경우 빈 상태로 설정
                }
            } catch (e: Exception) {
                _movieDetailUiState.value = MovieDetailUiState()      // 예외 발생 시 빈 상태로 설정
            }
        }
    }

    // 영화 제목으로 유효한 데이터인지 확인
    private fun isValidMovieData(movie: MovieUiState): Boolean {
        return !movie.movieName.isNullOrBlank()
    }
}