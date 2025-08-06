package com.example.mvc_project.presenter.movieDetail

import com.example.mvc_project.presenter.model.MovieUiState

class MovieDetailPresenter : MovieDetailContract.Presenter {

    private var view: MovieDetailContract.View? = null
    private var movieData: MovieUiState? = null

    override fun attachView(view: MovieDetailContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun loadMovieDetails(movie: MovieUiState) {
        this.movieData = movie
    }

    override fun onViewCreated() {
        movieData?.let { movie ->
            view?.displayMovieDetails(movie)
        } ?: run {
            view?.showError("영화 정보를 불러올 수 없습니다.")
        }
    }
}