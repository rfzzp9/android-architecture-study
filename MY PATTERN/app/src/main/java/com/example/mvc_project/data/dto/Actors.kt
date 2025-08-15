package com.example.mvc_project.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Actors(
    @SerialName("actor")
    val actorList: List<ActorsDetail> = emptyList()
)