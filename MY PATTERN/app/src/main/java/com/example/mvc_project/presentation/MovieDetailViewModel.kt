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
            } catch (e: Exception) {
                _movieDetailUiState.value = MovieDetailUiState()      // 예외 발생 시 빈 상태로 설정
            }
        }
    }

}