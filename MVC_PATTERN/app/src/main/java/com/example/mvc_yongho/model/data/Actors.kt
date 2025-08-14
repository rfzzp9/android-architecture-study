package com.example.mvc_yongho.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Actors(
    @SerialName("actor") val actor: List<ActorInfo> = emptyList()
)
