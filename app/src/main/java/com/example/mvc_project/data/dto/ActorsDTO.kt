package com.example.mvc_project.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorsDTO(
    @SerialName("actor")
    val actorList: List<ActorsDetailDTO> = emptyList()
)