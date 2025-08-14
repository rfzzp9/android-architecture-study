package com.joohyeong.architecture_pattern_study.domain

import com.joohyeong.architecture_pattern_study.data.ApiClient
import com.joohyeong.architecture_pattern_study.data.MovieService
import com.joohyeong.architecture_pattern_study.data.mapper.toMovie
import com.joohyeong.architecture_pattern_study.data.mapper.toMovieDetail
import com.joohyeong.architecture_pattern_study.data.response.MovieEntity

object MovieRepository {
    private val movieService = ApiClient.client.create(MovieService::class.java)
    private val movieCache = mutableListOf<MovieEntity>()

    suspend fun fetchMovies(): Movies {
        if (movieCache.isEmpty()) {
            movieCache.addAll(
                movieService.fetchMovies().body()
                    ?.data?.lastOrNull()?.result ?: emptyList()
            )
        }

        return Movies(values = movieCache.map { entity ->
            entity.toMovie()
        })
    }

    suspend fun fetchMovieDetailById(id: String): MovieDetail? {
        if (movieCache.isEmpty()) {
            movieCache.addAll(
                movieService.fetchMovies().body()
                    ?.data?.lastOrNull()?.result ?: emptyList()
            )
        }

        return movieCache.find { it.id == id }?.toMovieDetail()
    }
}
