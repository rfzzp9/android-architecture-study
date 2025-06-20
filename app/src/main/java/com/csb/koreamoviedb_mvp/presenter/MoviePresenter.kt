package com.csb.koreamoviedb_mvp.presenter

import com.csb.koreamoviedb_mvp.model.data.ResultMovieClass
import com.csb.koreamoviedb_mvp.model.repository.MovieRepository
import com.csb.koreamoviedb_mvp.tools.Filter
import com.csb.koreamoviedb_mvp.view._interface.SearchView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviePresenter(
    private val repository: MovieRepository
) {
    private var view: SearchView? = null

    private var cachedResults: List<ResultMovieClass> = emptyList()
    private var hasSearched: Boolean = false
    private var lastSearchText: String = ""
    private var lastSelectedFilter: Filter = Filter.FILTER_ALL

    // 뷰 재연결 시 기존 상태 복원?용
    fun attachView(view: SearchView) {
        this.view = view
        view.restoreSearchInput(lastSearchText, lastSelectedFilter)

        if (hasSearched) {
            if (cachedResults.isNotEmpty()) {
                view.showResults(cachedResults)
            } else {
                view.showEmptyMessage()
            }
        }
    }

    fun detachView() {
        view = null
    }

    //검색
    fun search(keyword: String, filter: Filter) {
        lastSearchText = keyword
        lastSelectedFilter = filter
        view?.showLoading()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.searchMovies(keyword, filter)
                cachedResults = result
                hasSearched = true
                withContext(Dispatchers.Main) {
                    if (result.isNotEmpty()) {
                        view?.showResults(result)
                    } else {
                        view?.showEmptyMessage()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    cachedResults = emptyList()
                    hasSearched = true
                    view?.showEmptyMessage()
                }
            } finally {
                withContext(Dispatchers.Main) {
                    view?.hideLoading()
                }
            }
        }
    }
}
