package com.example.mvc_project.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Directors(
    @SerialName("director")
    val directorList: List<DirectorsDetail> = emptyList()
)