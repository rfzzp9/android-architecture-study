package com.example.mvc_yongho.presenter

import com.example.mvc_yongho.model.data.MovieInfo

interface MainContract {

    interface View {
        fun showMovies(movies: List<MovieInfo>)
        fun showError(message: String)
    }

    interface Presenter {
        fun searchMovies(title: String)
        fun onDestroy()
    }

}
