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
            val result = MovieRepository.fetchMovieDetailById(movieId)
            if (result.isSuccess) {
                view.showMovieDetail(
                    result.getOrNull() ?: return@launch view.showLoadMovieDetailError()
                )
            } else {
                view.showLoadMovieDetailError()
            }
        }
    }
}