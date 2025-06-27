package com.example.mvc_project.model.network

import com.example.mvc_project.BuildConfig
import com.example.mvc_project.model.dto.ResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("openapi-data2/wisenut/search_api/search_json2.jsp")
    suspend fun fetchMovieList(
        @Query("collection") collection: String = "kmdb_new2",
        @Query("detail") detail: String = "Y",
        @Query("ServiceKey") serviceKey: String = BuildConfig.SERVICE_KEY,
    ): Response<ResponseDTO>
}