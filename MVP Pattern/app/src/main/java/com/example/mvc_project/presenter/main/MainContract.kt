package com.example.mvc_project.presenter.main

import com.example.mvc_project.presenter.model.MovieUiState

interface MainContract {

    interface View {
        fun showMovieList(movies: List<MovieUiState>)
        fun showError(message: String)
        fun showMovieDetail(movie: MovieUiState)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun loadMovieList()
        fun onMovieItemClicked(movie: MovieUiState)
    }
}