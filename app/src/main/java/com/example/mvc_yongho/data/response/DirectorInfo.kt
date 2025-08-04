package com.example.mvc_yongho.data.response

import kotlinx.serialization.Serializable

@Serializable
data class DirectorInfo(
    val directorNm: String,
    val directorEnNm: String,
    val directorId: String
)
