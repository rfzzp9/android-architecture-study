package com.example.mvc_project.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DirectorsDetail(
    @SerialName("directorNm")
    val directorName: String,
    @SerialName("directorEnNm")
    val directorEnName: String,
    val directorId: String,
)