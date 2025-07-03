package com.csb.koreamoviedb_mvvm.state

import com.csb.koreamoviedb_mvvm.model.data.ResultMovieClass
import com.csb.koreamoviedb_mvvm.tools.Filter

data class SearchState(
    val searchText: String = "",
    val selectedFilter: Filter = Filter.FILTER_ALL,
    val resultList: List<ResultMovieClass> = emptyList(),
    val hasSearched: Boolean = false,
    val isLoading: Boolean = false
)