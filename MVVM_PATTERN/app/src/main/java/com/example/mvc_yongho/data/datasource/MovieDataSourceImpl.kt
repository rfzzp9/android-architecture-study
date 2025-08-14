package com.example.mvc_yongho.data.datasource

import com.example.mvc_yongho.data.model.KmdbResponse
import com.example.mvc_yongho.data.api.KmdbService
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(private val kmdbService: KmdbService) :
    MovieDataSource {
    override suspend fun searchMovies(title: String): Result<KmdbResponse> {
        return runCatching {
            kmdbService.searchMovies(title = title)
        }
    }
}
