package com.example.mvc_project.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieData(
    @SerialName("DOCID")
    val docId: String,
    val movieId: String,
    val movieSeq: String,
    val title: String,
    val titleEng: String,
    val titleOrg: String,
    val titleEtc: String,
    val prodYear: String,
    @SerialName("directors")
    val directorList: Directors,
    @SerialName("actors")
    val actorList: Actors,
    val nation: String,
    val company: String,
    @SerialName("plots")
    val plotList: Plots,
    val runtime: String,
    val rating: String,
    val genre: String,
    @SerialName("posters")
    val posterUrl: String
)