package com.example.mvc_yongho.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KmdbResponse(
    @SerialName("Query") val query: String,
    @SerialName("KMAQuery") val kmaQuery: String,
    @SerialName("TotalCount") val totalCount: Int,
    @SerialName("Data") val data: List<CollectionData>
)

