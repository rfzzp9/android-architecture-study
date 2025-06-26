package com.joohyeong.architecture_pattern_study.presentation.moviedetail

import com.joohyeong.architecture_pattern_study.domain.MovieDetail

interface MovieDetailContract {
    interface View {
        fun showMovieDetail(movieDetail: MovieDetail)
        fun showLoadMovieDetailError()
    }

    interface Presenter {
        fun loadMovieDetail(movieId: String)
    }
}