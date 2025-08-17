package com.csb.koreamoviedb_mvvm.presentation.search

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csb.domain.MovieRepository
import com.csb.presentation.Filter
import com.csb.presentation.search.screen.SearchIntent
import com.csb.presentation.search.screen.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchState())
    val uiState: StateFlow<SearchState> = _uiState.asStateFlow()

    //사용자 입력을 인텐트로 전달
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun processIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.EnterText -> {
                _uiState.update { it.copy(searchText = intent.text) }
            }
            is SearchIntent.SelectFilter -> {
                _uiState.update { it.copy(selectedFilter = intent.filter) }
            }
            is SearchIntent.ClickSearchButton -> {
                search()
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun search() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            //현재상태
            val current = _uiState.value
            val query = current.searchText.trim()
            val filter = current.selectedFilter

            val result = movieRepository.getMovies(
                query = if (filter == Filter.FILTER_ALL) query else null,
                title = if (filter == Filter.FILTER_TITLE) query else null,
                actor = if (filter == Filter.FILTER_ACTOR) query else null,
                director = if (filter == Filter.FILTER_DIRECTOR) query else null
            )

            //최종 ui 업데이트
            _uiState.update {
                it.copy(
                    resultList = result,
                    hasSearched = true,
                    isLoading = false
                )
            }
        }
    }
}