package com.example.mvc_project.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("Query")
    val query: String? = null,
    @SerialName("KMAQuery")
    val kmaQuery: String? = null,
    @SerialName("TotalCount")
    val totalCount: Int? = null,
    @SerialName("Data")
    val dataList: List<ColumnDTO>
)