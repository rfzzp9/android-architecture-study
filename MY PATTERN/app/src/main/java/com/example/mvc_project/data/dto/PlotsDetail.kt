package com.example.mvc_project.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlotsDetail(
    val plotLang: String,
    val plotText: String
)