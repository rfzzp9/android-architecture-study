package com.example.mvc_yongho.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ActorInfo(
    val actorNm: String,
    val actorEnNm: String,
    val actorId: String
)
