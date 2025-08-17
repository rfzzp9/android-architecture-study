package com.example.mvc_yongho.data.response

import kotlinx.serialization.Serializable

@Serializable
data class Actors(
    val actor: List<ActorInfo> = emptyList()
)
