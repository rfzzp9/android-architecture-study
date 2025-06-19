package com.example.mvc_yongho.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Directors(
    @SerialName("director") val director: List<DirectorInfo> = emptyList()
)
