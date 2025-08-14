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
            val result = MovieRepository.fetchMovies()
            if (result.isFailure) {
                // Handle error case, e.g., show a message to the user
                return@launch view.showLoadMoviesError()
            }
            view.showMovies(
                movies = result.getOrNull() ?: return@launch view.showLoadMoviesError()
            )
        }
    }
}