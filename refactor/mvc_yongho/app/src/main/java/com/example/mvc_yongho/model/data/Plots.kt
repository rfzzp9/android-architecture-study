package com.example.mvc_yongho.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Plots(
    @SerialName("plot") val plot: List<PlotInfo>
)
