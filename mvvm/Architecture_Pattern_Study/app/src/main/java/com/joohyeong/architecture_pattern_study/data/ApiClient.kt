package com.joohyeong.architecture_pattern_study.data

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object ApiClient {
    private val mediaType = "application/json; charset=UTF8".toMediaType()
    private val json = Json { ignoreUnknownKeys = true }
    private const val BASE_URL =
        "http://api.koreafilm.or.kr"

    val client: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(mediaType))
            .build()
    }
}