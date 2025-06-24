package com.joohyeong.architecture_pattern_study.domain

import com.joohyeong.architecture_pattern_study.data.ApiClient
import com.joohyeong.architecture_pattern_study.data.MovieService
import com.joohyeong.architecture_pattern_study.data.mapper.toMovie
import com.joohyeong.architecture_pattern_study.data.mapper.toMovieDetail
import com.joohyeong.architecture_pattern_study.data.response.MovieEntity

object MovieRepository {
    private val movieService = ApiClient.client.create(MovieService::class.java)
    private val movieCache = mutableListOf<MovieEntity>()

    suspend fun fetchMovies(): Result<List<Movie>> {
        ensureMovieCache()
            .onFailure { return Result.failure(it) }

        return Result.success(
            movieCache.map { it.toMovie() }
        )
    }

    suspend fun fetchMovieDetailById(id: String): Result<MovieDetail> {
        ensureMovieCache()
            .onFailure { return Result.failure(it) }

        return Result.success(
            movieCache.find { it.id == id }?.toMovieDetail()
                ?: return Result.failure(IllegalStateException("Movie not found"))
        )
    }

    private suspend fun ensureMovieCache(): Result<Unit> {
        if (movieCache.isNotEmpty()) return Result.success(Unit)

        val response = movieService.fetchMovies()
        if (!response.isSuccessful) {
            return Result.failure(IllegalStateException("Failed to fetch movies"))
        }

        val movies = response.body()
            ?.data?.lastOrNull()?.result ?: emptyList()

        movieCache.addAll(movies)

        return Result.success(Unit)
    }
}
