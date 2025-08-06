package com.example.mvc_project.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorsDetailDTO(
    @SerialName("actorNm") val actorNm: String,
    @SerialName("actorEnNm") val actorEnNm: String,
    @SerialName("actorId") val actorId: String
)
