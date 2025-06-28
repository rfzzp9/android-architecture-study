package com.example.mvc_project.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DirectorsDetailDTO(
    val directorNm: String,
    val directorEnNm: String,
    val directorId: String,
)
