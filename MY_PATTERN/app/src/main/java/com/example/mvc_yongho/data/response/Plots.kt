package com.example.mvc_yongho.data.response

import kotlinx.serialization.Serializable

@Serializable
data class Plots(
    val plot: List<PlotInfo> = emptyList()
)
