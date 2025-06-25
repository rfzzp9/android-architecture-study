package com.example.mvc_project.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DirectorsDetailDTO(
    @SerialName("directorNm")
    val directorNm: String,
    @SerialName("directorEnNm")
    val directorEnNm: String,
    @SerialName("directorId")
    val directorId: String,
)
