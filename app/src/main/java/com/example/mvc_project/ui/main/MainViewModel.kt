package com.example.mvc_project.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.mvc_project.data.MovieListRepository
import com.example.mvc_project.domain.model.MovieUiState
import com.example.mvc_project.view.mapper.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
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

    class MainViewModelFactory(
        private val movieListRepository: MovieListRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(
            modelClass: Class<T>,
            extras: CreationExtras,
        ): T {
            return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                MainViewModel(movieListRepository) as T
            } else {
                throw IllegalArgumentException()
            }
        }
    }
}