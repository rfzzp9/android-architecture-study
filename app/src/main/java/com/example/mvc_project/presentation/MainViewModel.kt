package com.example.mvc_project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvc_project.data.MovieListRepository
import com.example.mvc_project.presentation.mapper.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
) : ViewModel() {

    private val _movieListStateFlow = MutableStateFlow<List<MovieUiState>>(emptyList())
    val movieListStateFlow = _movieListStateFlow.asStateFlow()

    init {
        fetchMovieList()
    }

    private fun fetchMovieList() = viewModelScope.launch {
        val response = movieListRepository.fetchMovieList()
        val movieList: List<MovieUiState> =
            response.dataList.first().resultList.map { it.toUiModel() }
        _movieListStateFlow.update {
            movieList
        }
    }

}