package com.csb.koreamoviedb_mvvm.presentation.search

import com.csb.koreamoviedb_mvvm.domain.ResultMovieClass
import com.csb.koreamoviedb_mvvm.Filter

data class SearchState(
    //검색어
    val searchText: String = "",
    //필터
    val selectedFilter: Filter = Filter.FILTER_ALL,
    //결과 리스트
    val resultList: List<ResultMovieClass> = emptyList(),
    //한번검색하면 true로바뀜
    val hasSearched: Boolean = false,
    //로딩상태
    val isLoading: Boolean = false
)