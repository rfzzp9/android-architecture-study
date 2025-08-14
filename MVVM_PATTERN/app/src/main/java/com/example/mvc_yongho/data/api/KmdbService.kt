package com.example.mvc_yongho.data.api

import com.example.mvc_yongho.BuildConfig
import com.example.mvc_yongho.data.model.KmdbResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KmdbService {
    @GET("openapi-data2/wisenut/search_api/search_json2.jsp")
    suspend fun searchMovies(
        @Query("collection") collection: String = "kmdb_new2",
        @Query("detail") detail: String = "Y",
        @Query("ServiceKey") serviceKey: String = BuildConfig.SERVICE_KEY,
        @Query("title") title: String
    ): KmdbResponse
}
