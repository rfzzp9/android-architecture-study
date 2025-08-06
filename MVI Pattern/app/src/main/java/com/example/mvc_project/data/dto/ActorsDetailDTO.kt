package com.example.mvc_project.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorsDetailDTO(
    @SerialName("actorNm")
    val actorName: String,
    @SerialName("actorEnNm")
    val actorEnName: String,
    val actorId: String
)