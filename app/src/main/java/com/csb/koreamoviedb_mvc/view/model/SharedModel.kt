package com.csb.koreamoviedb_mvc.view.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.csb.koreamoviedb_mvc.model.data.ResultMovieClass

class SharedModel : ViewModel() {
    var resultMovieClass by mutableStateOf(
        ResultMovieClass(
            title = "",
            directors = emptyList(),
            actors = emptyList(),
            plots = emptyList(),
            detailInfo = "",
            posters = "",
            runtime = "",
            ratingGrade = "",
            releaseDate = ""
        )
    )
        private set

    // 값 설정함수
    fun setData(resultMovieClass: ResultMovieClass) {
        this.resultMovieClass = resultMovieClass
    }
}