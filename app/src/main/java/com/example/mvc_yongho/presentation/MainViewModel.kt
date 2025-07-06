package com.example.mvc_yongho.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvc_yongho.domain.repository.MovieRepository
import com.example.mvc_yongho.presentation.model.MovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Empty)
    val uiState = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.SearchMovie -> {
                _searchQuery.value = intent.query
                searchMovies(intent.query)
            }
        }
    }

    private fun searchMovies(title: String) {
        _uiState.value = MovieUiState.Loading
        viewModelScope.launch {
            movieRepository.searchMovies(title)
                .onSuccess { movies ->
                    if (movies.isEmpty()) {
                        _uiState.value = MovieUiState.Empty
                    } else {
                        _uiState.value = MovieUiState.HasMovies(movies)
                    }
                }
                .onFailure { e ->
                    _uiState.value = MovieUiState.Error(e.message ?: SEARCH_NOT_FOUND)
                }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    companion object {
        private const val SEARCH_NOT_FOUND = "검색 결과가 없습니다."
    }
}
