package com.joohyeong.architecture_pattern_study.domain

import com.joohyeong.architecture_pattern_study.data.ApiClient
import com.joohyeong.architecture_pattern_study.data.MovieService
import com.joohyeong.architecture_pattern_study.data.response.MovieEntity

class MovieRepository {
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
            Movie(
                id = entity.id, // Assuming repRlsDate is unique for each movie
                title = entity.title,
                posterUrl = entity.posters,
                audienceRating = entity.rating,
                runtime = entity.runtime
            )
        })
    }

    suspend fun fetchMovieById(id: String): Movie? {
        if (movieCache.isEmpty()) {
            movieCache.addAll(
                movieService.fetchMovies().body()
                    ?.data?.lastOrNull()?.result ?: emptyList()
            )
        }

        return movieCache.find { it.id == id }?.let { entity ->
            Movie(
                id = entity.id,
                title = entity.title,
                posterUrl = entity.posters,
                audienceRating = entity.rating,
                runtime = entity.runtime
            )
        }
    }
}
