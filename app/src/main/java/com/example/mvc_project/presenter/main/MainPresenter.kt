package com.example.mvc_project.presenter.main

import com.example.mvc_project.model.usecase.GetMovieListUseCase
import com.example.mvc_project.presenter.model.MovieUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainPresenter(
    private val getMovieListUseCase: GetMovieListUseCase
) : MainContract.Presenter {

    private var view: MainContract.View? = null
    private var job: Job? = null

    override fun attachView(view: MainContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
        job?.cancel()
    }

    override fun loadMovieList() {
        job = CoroutineScope(Dispatchers.IO).launch {
            try {
                val movies = getMovieListUseCase.execute()

                withContext(Dispatchers.Main) {
                    view?.showMovieList(movies)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view?.showError("영화 목록을 불러오는데 실패했습니다.")
                }
            }
        }
    }

    override fun onMovieItemClicked(movie: MovieUiState) {
        view?.showMovieDetail(movie)
    }
}