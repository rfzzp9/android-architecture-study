package com.example.mvc_yongho.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionData(
    @SerialName("CollName") val collName: String,
    @SerialName("TotalCount") val totalCount: Int,
    @SerialName("Count") val count: Int,
    @SerialName("Result") val result: List<MovieInfo> = emptyList()
)
