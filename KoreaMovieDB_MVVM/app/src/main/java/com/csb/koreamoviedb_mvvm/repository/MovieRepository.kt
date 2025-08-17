package com.csb.koreamoviedb_mvvm.repository

import com.csb.koreamoviedb_mvvm.domain.ResultMovieClass

interface MovieRepository {
    suspend fun getMovies(
        query: String? = null,
        title: String? = null,
        actor: String? = null,
        director: String? = null
    ): List<ResultMovieClass>
}
