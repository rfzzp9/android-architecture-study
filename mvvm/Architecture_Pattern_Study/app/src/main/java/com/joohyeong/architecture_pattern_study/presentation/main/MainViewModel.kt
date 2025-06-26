package com.joohyeong.architecture_pattern_study.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohyeong.architecture_pattern_study.domain.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<MainUIState> = MutableStateFlow(MainUIState.Loading)
    val uiState: StateFlow<MainUIState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }
    private fun loadMovies() {
        viewModelScope.launch {
            val result = MovieRepository.fetchMovies()
            if (result.isFailure) {
                _uiState.update {
                    MainUIState.Error(result.exceptionOrNull() ?: Throwable("Unknown error"))
                }
                return@launch
            }
            _uiState.update {
                MainUIState.Success(
                    movies = result.getOrNull() ?: emptyList()
                )
            }
        }
    }
}

