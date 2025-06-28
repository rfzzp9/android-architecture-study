package com.example.mvc_project.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorsDetailDTO(
    val actorNm: String,
    val actorEnNm: String,
    val actorId: String
)
