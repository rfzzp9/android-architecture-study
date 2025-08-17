package com.example.mvc_yongho.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ActorInfo(
    val actorNm: String,
    val actorEnNm: String,
    val actorId: String
)
