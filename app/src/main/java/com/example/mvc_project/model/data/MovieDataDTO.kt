package com.example.mvc_project.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDataDTO(
    @SerialName("DOCID")
    val docId: String,
    val movieId: String,
    val movieSeq: String,
    val title: String,
    val titleEng: String,
    val titleOrg: String,
    val titleEtc: String,
    val prodYear: String,
    val directors: DirectorsDTO,
    val actors: ActorsDTO,
    val nation: String,
    val company: String,
    val plots: PlotsDTO,
    val runtime: String,
    val rating: String,
    val genre: String,
    val posters: String
)
