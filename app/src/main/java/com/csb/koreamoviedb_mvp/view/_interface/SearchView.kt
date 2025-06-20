package com.csb.koreamoviedb_mvp.view._interface

import com.csb.koreamoviedb_mvp.model.data.ResultMovieClass
import com.csb.koreamoviedb_mvp.tools.Filter

interface SearchView {
    fun showLoading()
    fun hideLoading()
    fun showResults(results: List<ResultMovieClass>)
    fun showEmptyMessage()
    fun restoreSearchInput(searchText: String, filter: Filter)
}