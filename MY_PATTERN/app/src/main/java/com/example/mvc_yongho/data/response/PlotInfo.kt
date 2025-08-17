package com.example.mvc_yongho.data.response

import kotlinx.serialization.Serializable

@Serializable
data class PlotInfo(
    val plotLang: String,
    val plotText: String
)
