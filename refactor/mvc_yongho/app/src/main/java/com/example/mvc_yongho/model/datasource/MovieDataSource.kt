package com.example.mvc_yongho.model.datasource

import com.example.mvc_yongho.model.data.KmdbResponse

interface MovieDataSource {
    suspend fun searchMovies(title: String): Result<KmdbResponse>
}
