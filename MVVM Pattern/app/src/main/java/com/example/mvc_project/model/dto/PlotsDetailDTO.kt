package com.example.mvc_project.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlotsDetailDTO(
    val plotLang: String,
    val plotText: String
)