package com.example.mvc_yongho.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlotInfo(
    @SerialName("plotLang") val plotLang: String,
    @SerialName("plotText") val plotText: String
)
