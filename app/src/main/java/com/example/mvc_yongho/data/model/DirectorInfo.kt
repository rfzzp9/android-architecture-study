package com.example.mvc_yongho.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DirectorInfo(
    val directorNm: String,
    val directorEnNm: String,
    val directorId: String
)
