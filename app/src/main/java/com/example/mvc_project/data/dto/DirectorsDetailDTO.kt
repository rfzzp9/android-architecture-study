package com.example.mvc_project.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DirectorsDetailDTO(
    @SerialName("directorNm")
    val directorName: String,
    @SerialName("directorEnNm")
    val directorEnName: String,
    val directorId: String,
)