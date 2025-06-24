package com.joohyeong.architecture_pattern_study.presentation.main

import com.joohyeong.architecture_pattern_study.domain.Movie

interface MainContract {
    interface View {
        fun showMovies(movies: List<Movie>)
        fun showLoadMoviesError()
    }

    interface Presenter {
        fun loadMovies()
    }
}