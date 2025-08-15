package com.example.mvc_project.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Plots(
    @SerialName("plot")
    val plotList: List<PlotsDetail> = emptyList()
)