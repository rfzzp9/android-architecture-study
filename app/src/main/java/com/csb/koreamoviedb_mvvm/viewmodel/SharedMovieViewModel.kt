package com.csb.koreamoviedb_mvvm.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.csb.koreamoviedb_mvvm.model.data.ResultMovieClass
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SharedMovieViewModel @Inject constructor() : ViewModel() {

    //선택된 영화정보
    private val _selectedMovie = mutableStateOf<ResultMovieClass?>(null)
    val selectedMovie: State<ResultMovieClass?> = _selectedMovie
    fun setSelectedMovie(movie: ResultMovieClass) {
        _selectedMovie.value = movie
    }
}