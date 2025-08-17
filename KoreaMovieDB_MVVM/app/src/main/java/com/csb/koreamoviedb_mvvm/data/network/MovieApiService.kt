package com.csb.koreamoviedb_mvvm.data.network

import com.csb.koreamoviedb_mvvm.data.model.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("/openapi-data2/wisenut/search_api/search_json2.jsp")
    suspend fun searchMovie(
        @Query("ServiceKey") serviceKey: String,
        @Query("collection") collection: String = "kmdb_new2",
        @Query("listCount") listCount: Int = 500,
        @Query("query") query: String? = null,
        @Query("title") title: String? = null,
        @Query("actor") actor: String? = null,
        @Query("director") director: String? = null,
        @Query("sort") sort: String = "prodYear,1"
    ): MovieSearchResponse
}