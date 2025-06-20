package com.joohyeong.architecture_pattern_study.data

import com.joohyeong.architecture_pattern_study.BuildConfig
import com.joohyeong.architecture_pattern_study.data.response.MovieResultContainer
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("/openapi-data2/wisenut/search_api/search_json2.jsp?")
    suspend fun fetchMovies(
        @Query("collection") collection: String = "kmdb_new2",
        @Query("ServiceKey") serviceKey: String = BuildConfig.SERVICE_KEY,
        @Query("startCount") startCount: Int = 0,
        @Query("listCount") listCount: Int = 20,
    ): Response<MovieResultContainer>
}