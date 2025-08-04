package com.example.mvc_yongho.data.repository

import com.example.mvc_yongho.data.datasource.MovieDataSource
import com.example.mvc_yongho.data.response.toMovie
import com.example.mvc_yongho.domain.model.Movie
import com.example.mvc_yongho.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieDataSource: MovieDataSource) :
    MovieRepository {
    override suspend fun searchMovies(title: String): Result<List<Movie>> =
        withContext(Dispatchers.IO) {
            if (title.isBlank()) return@withContext Result.failure(IllegalStateException(SEARCH_QUERY_EMPTY))

            try {
                movieDataSource.searchMovies(title).fold(
                    onSuccess = { kmdbMovie ->
                        val movies = if (kmdbMovie.totalCount == 0) {
                            emptyList()
                        } else {
                            kmdbMovie.data.flatMap { it.result }.map { it.toMovie() }
                        }
                        Result.success(movies)
                    },
                    onFailure = { Result.failure(it) }
                )
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    companion object {
        private const val SEARCH_QUERY_EMPTY = "검색어를 입력해주세요."
    }
}
