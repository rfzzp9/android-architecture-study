package com.example.mvc_project.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlotsDetailDTO(
    @SerialName("plotLang")
    val plotLang: String,
    @SerialName("plotText")
    val plotText: String
)