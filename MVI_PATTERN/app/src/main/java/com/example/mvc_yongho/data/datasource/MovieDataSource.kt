package com.example.mvc_yongho.data.datasource

import com.example.mvc_yongho.data.model.KmdbResponse

interface MovieDataSource {
    suspend fun searchMovies(title: String): Result<KmdbResponse>
}
