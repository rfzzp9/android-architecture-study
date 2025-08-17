package com.example.mvc_yongho.data.datasource

import com.example.mvc_yongho.data.response.KmdbResponse

interface MovieDataSource {
    suspend fun searchMovies(title: String): Result<KmdbResponse>
}
