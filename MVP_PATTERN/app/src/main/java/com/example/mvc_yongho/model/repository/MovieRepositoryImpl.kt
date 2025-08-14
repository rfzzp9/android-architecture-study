package com.example.mvc_yongho.model.repository

import com.example.mvc_yongho.model.data.MovieInfo
import com.example.mvc_yongho.model.datasource.MovieDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieDataSource: MovieDataSource) :
    MovieRepository {
    override suspend fun searchMovies(title: String): Result<List<MovieInfo>> {
        return withContext(Dispatchers.IO) {
            try {
                movieDataSource.searchMovies(title).mapCatching { kmdbMovie ->
                    if (kmdbMovie.totalCount == 0) {
                        throw Exception(SEARCH_NOT_FOUND)
                    }

                    kmdbMovie.data.flatMap { it.result }
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    companion object {
        private const val SEARCH_NOT_FOUND = "검색 결과가 없습니다."
    }
}
