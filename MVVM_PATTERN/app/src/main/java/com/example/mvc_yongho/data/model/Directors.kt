package com.example.mvc_yongho.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Directors(
    val director: List<DirectorInfo> = emptyList()
)
