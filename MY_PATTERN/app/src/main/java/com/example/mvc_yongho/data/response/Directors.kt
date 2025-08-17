package com.example.mvc_yongho.data.response

import kotlinx.serialization.Serializable

@Serializable
data class Directors(
    val director: List<DirectorInfo> = emptyList()
)
