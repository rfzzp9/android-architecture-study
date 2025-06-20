package com.example.mvc_project.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDTO(
    @SerialName("Query")
    val query: String,
    @SerialName("KMAQuery")
    val kmaQuery: String,
    @SerialName("TotalCount")
    val totalCount: Int,
    @SerialName("Data")
    val data: List<ColumnDTO>
)
