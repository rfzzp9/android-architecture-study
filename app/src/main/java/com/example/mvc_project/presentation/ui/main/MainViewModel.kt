package com.example.mvc_project.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.mvc_project.data.MovieListDataSource
import com.example.mvc_project.data.MovieListRepository
import com.example.mvc_project.data.api.ApiService
import com.example.mvc_project.data.api.RetrofitInstance
import com.example.mvc_project.domain.model.MovieUiState
import com.example.mvc_project.presentation.sideeffect.MainSideEffect
import com.example.mvc_project.presentation.ui.main.mapper.toUiModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val movieListRepository: MovieListRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(MainViewState())
    val viewState: StateFlow<MainViewState> = _viewState.asStateFlow()

    // SideEffect를 위한 별도 채널
    private val _sideEffect = Channel<MainSideEffect>()
    val sideEffect: Flow<MainSideEffect> = _sideEffect.receiveAsFlow()

    private val _intent = MutableSharedFlow<MainIntent>()

    init {
        handleIntents()
        processIntent(MainIntent.LoadMovieList)
    }

    fun processIntent(intent: MainIntent) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            _intent.collect { intent ->
                when (intent) {
                    is MainIntent.LoadMovieList -> loadMovieList()
                    is MainIntent.ShowMovieDetail -> showMovieDetail(intent.movie)
                    is MainIntent.RetryLoadMovieList -> retryLoadMovieList()
                }
            }
        }
    }

    private suspend fun loadMovieList() {
        _viewState.update { currentState ->
            currentState.copy(isLoading = true, error = null)
        }

        try {
            val response = movieListRepository.fetchMovieList()
            val movieList: List<MovieUiState> =
                response.dataList.first().resultList.map { it.toUiModel() }

            _viewState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    movies = movieList,
                    error = null
                )
            }
        } catch (e: Exception) {
            _viewState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    error = "영화 목록을 불러오는데 실패했습니다."
                )
            }
            // Error SideEffect 전송
            _sideEffect.send(MainSideEffect.ShowErrorMessage("영화 목록을 불러오는데 실패했습니다."))
        }
    }

    private suspend fun retryLoadMovieList() {
        loadMovieList()
    }

    private suspend fun showMovieDetail(movie: MovieUiState) {
        // SideEffect로 다이얼로그 표시를 UI에 위임
        _sideEffect.send(MainSideEffect.ShowMovieDetailDialog(movie))
    }
}

class MainViewModelFactory private constructor(
    private val movieListRepository: MovieListRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(movieListRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        fun create(): MainViewModelFactory {
            val apiService = RetrofitInstance.getInstance().create(ApiService::class.java)
            val dataSource = MovieListDataSource(apiService)
            val repository = MovieListRepository(dataSource)
            return MainViewModelFactory(repository)
        }
    }
}
