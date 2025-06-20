package com.csb.koreamoviedb_mvc.controller

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.csb.koreamoviedb_mvc.model.MovieModel
import com.csb.koreamoviedb_mvc.model.dataclass.ResultMovieClass
import com.csb.koreamoviedb_mvc.tools.Filter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieController(
    val context: Context,
    private val model: MovieModel
) {

    // 검색대기 프로그래스 다이얼로그
    private val _progressDialogIsShowing = mutableStateOf(false)
    val progressDialogIsShowing: State<Boolean> get() = _progressDialogIsShowing

    // 앱 초기 실행시 검색전상태
    private val _hasSearched = mutableStateOf(false)
    val hasSearched: State<Boolean> get() = _hasSearched

    // 검색어
    private val _searchText = mutableStateOf("")
    val searchText: State<String> get() = _searchText
    fun updateSearchText(newText: String) {
        _searchText.value = newText
    }

    // 검색필터
    private val _selectedFilter = mutableStateOf(Filter.FILTER_ALL)
    val selectedFilter: State<Filter> get() = _selectedFilter
    fun updateSelectedFilter(newFilter: Filter) {
        _selectedFilter.value = newFilter
    }

    //검색결과
    private val _resultListState = mutableStateOf<List<ResultMovieClass>>(emptyList())
    val resultList: List<ResultMovieClass> get() = _resultListState.value

    fun updateResultList(newResult: List<ResultMovieClass>) {
        _resultListState.value = newResult
    }

    //검색 프로세스
    fun search() {
        CoroutineScope(Dispatchers.IO).launch {
            _progressDialogIsShowing.value = true

            try {
                val result = model.searchMovies(_searchText.value, _selectedFilter.value)
                updateResultList(result)
            } catch (e: Exception) {
                println("에러야: ${e.message}")
            } finally {
                _hasSearched.value = true
                _progressDialogIsShowing.value = false
            }

        }
    }
}