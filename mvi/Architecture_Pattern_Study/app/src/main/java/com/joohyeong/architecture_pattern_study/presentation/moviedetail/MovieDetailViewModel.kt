package com.joohyeong.architecture_pattern_study.presentation.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohyeong.architecture_pattern_study.data.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<MovieDetailUIState> =
        MutableStateFlow(MovieDetailUIState.Loading)
    val uiState: StateFlow<MovieDetailUIState> = _uiState.asStateFlow()

    fun processIntent(intent: MovieDetailIntent) {
        when (intent) {
            is MovieDetailIntent.LoadMovieDetail -> loadMovieDetail(intent.movieId)
        }
    }

    private fun loadMovieDetail(movieId: String) {
        viewModelScope.launch {
            val result = movieRepository.fetchMovieDetailById(movieId)
            result.onSuccess {
                _uiState.update {
                    MovieDetailUIState.Success(
                        movieDetail = result.getOrNull() ?: return@launch
                    )
                }
            }.onFailure {
                _uiState.update {
                    MovieDetailUIState.Error(result.exceptionOrNull() ?: Throwable("Unknown error"))
                }
            }
        }
    }
}