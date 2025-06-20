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
    private val repository: MovieRepository // 모델 계층 의존성 (API 요청용)
) {
    private var view: SearchView? = null // View 인터페이스 참조 (UI 갱신 목적)

    // 이전 검색 결과 및 검색 상태 보관용
    private var cachedResults: List<ResultMovieClass> = emptyList()
    private var hasSearched: Boolean = false

    // 마지막 검색어 및 필터 상태 저장용
    private var lastSearchText: String = ""
    private var lastSelectedFilter: Filter = Filter.FILTER_ALL

    // View가 연결될 때 이전 상태 복원 (검색어, 필터, 결과)
    fun attachView(view: SearchView) {
        this.view = view

        // 검색어, 필터 UI 상태 복원
        view.restoreSearchInput(lastSearchText, lastSelectedFilter)

        // 이전 검색 결과 복원
        if (hasSearched) {
            if (cachedResults.isNotEmpty()) {
                view.showResults(cachedResults)
            } else {
                view.showEmptyMessage()
            }
        }
    }

    // View 연결 해제 (메모리 누수 방지)
    fun detachView() {
        view = null
    }

    // 검색 처리 함수
    fun search(keyword: String, filter: Filter) {
        // 입력값 저장 (상태 복원용)
        lastSearchText = keyword
        lastSelectedFilter = filter

        // 로딩 표시
        view?.showLoading()

        // 백그라운드에서 API 요청
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.searchMovies(keyword, filter) // API 호출
                cachedResults = result // 결과 캐시
                hasSearched = true // 검색 여부 상태 갱신

                // UI 갱신은 메인 스레드에서 실행
                withContext(Dispatchers.Main) {
                    if (result.isNotEmpty()) {
                        view?.showResults(result)
                    } else {
                        view?.showEmptyMessage()
                    }
                }
            } catch (e: Exception) {
                // 에러 발생 시 빈 결과 처리
                withContext(Dispatchers.Main) {
                    cachedResults = emptyList()
                    hasSearched = true
                    view?.showEmptyMessage()
                }
            } finally {
                // 로딩 종료 (무조건 실행됨)
                withContext(Dispatchers.Main) {
                    view?.hideLoading()
                }
            }
        }
    }
}
