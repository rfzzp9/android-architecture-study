package com.example.mvc_project.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlotsDTO(
    val plot: List<PlotsDetailDTO> = emptyList()
)
