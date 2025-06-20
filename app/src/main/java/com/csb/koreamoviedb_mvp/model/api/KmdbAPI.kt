package com.csb.koreamoviedb_mvp.model.api


import com.csb.koreamoviedb_mvp.model.data.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface KmdbAPI {
    @GET("search_xml2.jsp?collection=kmdb_new2")
    fun searchMovies(
        @Query("ServiceKey") serviceKey: String,
        @Query("listCount") listCount: Int? = 500,
        @Query("type") type: String? = null,
        @Query("director") director: String? = null,
        @Query("title") title:String? = null,
        @Query("actor") actor: String? = null,
        @Query("query") query: String? = null,
        @Query("sort") sort: String? = "prodYear,1"
    ): Call<SearchResponse>
}