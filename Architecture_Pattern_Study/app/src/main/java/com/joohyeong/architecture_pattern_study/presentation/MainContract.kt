package com.joohyeong.architecture_pattern_study.presentation

import com.joohyeong.architecture_pattern_study.domain.Movie
import com.joohyeong.architecture_pattern_study.domain.Movies

interface MainContract {
    interface View {
        fun showMovies(movies: Movies)
    }

    interface Presenter {
        fun loadMovies()
    }
}