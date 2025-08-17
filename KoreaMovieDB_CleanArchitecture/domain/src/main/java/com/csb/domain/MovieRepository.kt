package com.csb.domain

interface MovieRepository {
    suspend fun getMovies(
        query: String? = null,
        title: String? = null,
        actor: String? = null,
        director: String? = null
    ): List<ResultMovieClass>
}
