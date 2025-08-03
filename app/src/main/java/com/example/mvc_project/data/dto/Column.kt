package com.example.mvc_project.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Column(
    @SerialName("CollName")
    val collName: String,
    @SerialName("TotalCount")
    val totalCount: Int,
    @SerialName("Count")
    val count: Int,
    @SerialName("Result")
    val resultList: List<MovieData> = emptyList(),
)