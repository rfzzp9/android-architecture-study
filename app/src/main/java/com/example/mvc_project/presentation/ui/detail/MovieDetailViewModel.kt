package com.example.mvc_project.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvc_project.R
import com.example.mvc_project.domain.model.MovieDetailUiState
import com.example.mvc_project.domain.model.MovieUiState
import com.example.mvc_project.presentation.sideeffect.MovieDetailSideEffect
import com.example.mvc_project.presentation.ui.detail.MovieDetailViewState
import com.example.mvc_project.presentation.ui.main.mapper.toMovieDetailUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailViewModel : ViewModel() {

    private val _viewState = MutableStateFlow(MovieDetailViewState())
    val viewState: StateFlow<MovieDetailViewState> = _viewState.asStateFlow()

    private val _sideEffect = Channel<MovieDetailSideEffect>()
    val sideEffect: Flow<MovieDetailSideEffect> = _sideEffect.receiveAsFlow()

    private val _intent = MutableSharedFlow<MovieDetailIntent>()

    init {
        handleIntents()
    }

    fun processIntent(intent: MovieDetailIntent) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            _intent.collect { intent ->
                when (intent) {
                    is MovieDetailIntent.LoadMovieDetail -> loadMovieDetail(intent.movie)
                    is MovieDetailIntent.DismissDialog -> dismissDialog()
                }
            }
        }
    }

    private suspend fun loadMovieDetail(movie: MovieUiState) {
        _viewState.update { currentState ->
            currentState.copy(isLoading = true, error = null)
        }

        try {
            if (isValidMovieData(movie)) {
                val movieDetail = movie.toMovieDetailUiState()

                _viewState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        movieDetail = movieDetail,
                        error = null
                    )
                }
            } else {
                val errorMessage = R.string.not_found_movieInfo.toString()
                _viewState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = errorMessage
                    )
                }
                _sideEffect.send(MovieDetailSideEffect.ShowErrorAndDismiss(errorMessage))
            }
        } catch (e: Exception) {
            val errorMessage = R.string.fail_load_movieInfo.toString()
            _viewState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    error = errorMessage
                )
            }
            _sideEffect.send(MovieDetailSideEffect.ShowErrorAndDismiss(errorMessage))
        }
    }

    private suspend fun dismissDialog() {
        _sideEffect.send(MovieDetailSideEffect.DismissDialog)
    }

    private fun isValidMovieData(movie: MovieUiState): Boolean {
        return !movie.movieName.isNullOrBlank()
    }

}