package com.joohyeong.architecture_pattern_study.presentation.main

import com.joohyeong.architecture_pattern_study.domain.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainPresenter(
    private val view: MainContract.View
) : MainContract.Presenter {
    override fun loadMovies() {
        CoroutineScope(Dispatchers.Main).launch {
            val movies = MovieRepository.fetchMovies()
            view.showMovies(
                movies = movies
            )
        }
    }
}