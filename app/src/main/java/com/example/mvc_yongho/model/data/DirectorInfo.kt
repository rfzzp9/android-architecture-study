package com.example.mvc_yongho.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DirectorInfo(
    @SerialName("directorNm") val directorNm: String,
    @SerialName("directorEnNm") val directorEnNm: String,
    @SerialName("directorId") val directorId: String
)
