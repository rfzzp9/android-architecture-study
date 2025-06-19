package com.example.mvc_yongho.model.repository

import com.example.mvc_yongho.model.data.MovieInfo
import com.example.mvc_yongho.model.datasource.MovieDataSource
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieDataSource: MovieDataSource) : MovieRepository {
    override suspend fun searchMovies(title: String): Result<List<MovieInfo>> {
        return movieDataSource.searchMovies(title).mapCatching { kmdbMovie ->
            if (kmdbMovie.totalCount == 0) {
                throw Exception("검색 결과가 없습니다.")
            }

            kmdbMovie.data.flatMap { it.result }
        }
    }
}
