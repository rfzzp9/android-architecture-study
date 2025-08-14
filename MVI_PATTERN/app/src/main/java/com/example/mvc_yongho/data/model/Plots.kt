package com.example.mvc_yongho.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Plots(
    val plot: List<PlotInfo> = emptyList()
)
