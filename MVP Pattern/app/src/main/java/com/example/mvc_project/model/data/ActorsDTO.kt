package com.example.mvc_project.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorsDTO(
    @SerialName("actor")
    val actor: List<ActorsDetailDTO> = emptyList()
)
