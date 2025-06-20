package com.example.mvc_yongho.presenter

import com.example.mvc_yongho.model.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val movieRepository: MovieRepository,
    private val view: MainContract.View
) : MainContract.Presenter {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun searchMovies(title: String) {
        if (title.isBlank()) {
            view.showError(message = EMPTY_TITLE)
            return
        }

        coroutineScope.launch {
            movieRepository.searchMovies(title = title)
                .onSuccess { movies ->
                    view.showMovies(movies)
                }
                .onFailure { exception ->
                    view.showError(message = exception.message ?: SEARCH_FAILED)
                }
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
    }

    companion object {
        private const val SEARCH_FAILED = "조회를 실패하였습니다."
        private const val EMPTY_TITLE = "검색어를 입력해주세요."
    }
}
