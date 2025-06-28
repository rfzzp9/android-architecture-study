package com.example.mvc_project.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DirectorsDTO(
    val director: List<DirectorsDetailDTO> = emptyList()
)