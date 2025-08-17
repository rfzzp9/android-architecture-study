package com.csb.koreamoviedb_mvvm.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csb.koreamoviedb_mvvm.model.data.ResultMovieClass
import com.csb.koreamoviedb_mvvm.model.repository.MovieRepository
import com.csb.koreamoviedb_mvvm.tools.Filter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    //검색필터링
    private val _selectedFilter = MutableStateFlow(Filter.FILTER_ALL)
    val selectedFilter: StateFlow<Filter> = _selectedFilter.asStateFlow()
    fun updateSelectedFilter(filter: Filter) {
        _selectedFilter.value = filter
    }

    //검색어
    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()
    fun updateSearchText(text: String) {
        _searchText.value = text
    }

    //검색된 결과 리스트
    private val _resultList = MutableStateFlow<List<ResultMovieClass>>(emptyList())
    val resultList: StateFlow<List<ResultMovieClass>> = _resultList.asStateFlow()

    //검색완료
    private val _hasSearched = MutableStateFlow(false)
    val hasSearched: StateFlow<Boolean> = _hasSearched.asStateFlow()

    //다이얼로그
    private var _dialogIsShowing = mutableStateOf(false)
    val dialogIsShowing: State<Boolean> = _dialogIsShowing

    //검색 프로세스
    fun search() {
        viewModelScope.launch {
            _dialogIsShowing.value = true
            val query = searchText.value.trim()
            val filter = selectedFilter.value

            val result = movieRepository.getMovies(
                query = if (filter == Filter.FILTER_ALL) query else null,
                title = if (filter == Filter.FILTER_TITLE) query else null,
                actor = if (filter == Filter.FILTER_ACTOR) query else null,
                director = if (filter == Filter.FILTER_DIRECTOR) query else null
            )

            _resultList.value = result
            _hasSearched.value = true
            _dialogIsShowing.value = false
        }
    }
}