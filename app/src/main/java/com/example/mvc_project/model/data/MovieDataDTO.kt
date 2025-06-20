package com.example.mvc_project.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDataDTO(
    @SerialName("DOCID")
    val docId: String,
    @SerialName("movieId")
    val movieId: String,
    @SerialName("movieSeq")
    val movieSeq: String,
    @SerialName("title")
    val title: String,
    @SerialName("titleEng")
    val titleEng: String,
    @SerialName("titleOrg")
    val titleOrg: String,
    @SerialName("titleEtc")
    val titleEtc: String,
    @SerialName("prodYear")
    val prodYear: String,
    @SerialName("directors")
    val directors: DirectorsDTO,
    @SerialName("actors")
    val actors: ActorsDTO,
    @SerialName("nation")
    val nation: String,
    @SerialName("company")
    val company: String,
    @SerialName("plots")
    val plots: PlotsDTO,
    @SerialName("runtime")
    val runtime: String,
    @SerialName("rating")
    val rating: String,
    @SerialName("genre")
    val genre: String,
    @SerialName("kmdbUrl")
    val kmdbUrl: String,
    @SerialName("posters")
    val posters: String
)
