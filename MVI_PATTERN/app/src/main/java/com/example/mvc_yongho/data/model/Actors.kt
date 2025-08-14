package com.example.mvc_yongho.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Actors(
    val actor: List<ActorInfo> = emptyList()
)
