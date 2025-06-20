package com.joohyeong.architecture_pattern_study.presentation.moviedetail

import com.joohyeong.architecture_pattern_study.domain.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailPresenter(
    private var view: MovieDetailContract.View
) : MovieDetailContract.Presenter {


    override fun loadMovieDetail(movieId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val movieDetail = MovieRepository.fetchMovieDetailById(movieId)
            if(movieDetail != null) {
                view.showMovieDetail(
                    movieDetail = movieDetail
                )
            }
        }
    }
}