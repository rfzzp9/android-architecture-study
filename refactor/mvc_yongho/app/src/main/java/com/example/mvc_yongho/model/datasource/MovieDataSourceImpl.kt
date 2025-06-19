package com.example.mvc_yongho.model.datasource

import com.example.mvc_yongho.model.data.KmdbResponse
import com.example.mvc_yongho.model.network.api.KmdbService
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(private val kmdbService: KmdbService) :
    MovieDataSource {
    override suspend fun searchMovies(title: String): Result<KmdbResponse> {
        return runCatching {
            kmdbService.searchMovies(title = title)
        }
    }
}
