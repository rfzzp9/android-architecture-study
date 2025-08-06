package com.example.mvc_project.presenter.movieDetail

import com.example.mvc_project.presenter.model.MovieUiState

interface MovieDetailContract {

    interface View {
        fun displayMovieDetails(movie: MovieUiState)
        fun showError(message: String)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun loadMovieDetails(movie: MovieUiState)
        fun onViewCreated()
    }
}