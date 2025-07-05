package com.example.mvc_project.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DirectorsDTO(
    @SerialName("director")
    val directorList: List<DirectorsDetailDTO> = emptyList()
)