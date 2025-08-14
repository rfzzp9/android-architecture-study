package com.example.mvc_yongho.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieInfo(
    @SerialName("DOCID") val docId: String,
    @SerialName("movieId") val movieId: String,
    @SerialName("movieSeq") val movieSeq: String,
    @SerialName("title") val title: String,
    @SerialName("titleEng") val titleEng: String,
    @SerialName("titleOrg") val titleOrg: String,
    @SerialName("titleEtc") val titleEtc: String,
    @SerialName("prodYear") val prodYear: String,
    @SerialName("directors") val directors: Directors,
    @SerialName("actors") val actors: Actors,
    @SerialName("nation") val nation: String,
    @SerialName("company") val company: String,
    @SerialName("plots") val plots: Plots,
    @SerialName("runtime") val runtime: String,
    @SerialName("rating") val rating: String,
    @SerialName("genre") val genre: String,
    @SerialName("kmdbUrl") val kmdbUrl: String,
    @SerialName("posters") val posters: String
) {
    val cleanTitle: String
        get() = title
            .replace("!HS", "")
            .replace("!HE ", "")
            .trim()

    val directorName: String
        get() = directors.director.firstOrNull()?.directorNm ?: "정보 없음"

    val plotText: String
        get() = plots.plot.firstOrNull()?.plotText ?: "줄거리 정보가 없습니다."

    val runtimeWithMinutes: String
        get() = if (runtime.isNotEmpty()) "${runtime}분" else "정보 없음"

    val posterUrl: String
        get() = posters.split("|").firstOrNull() ?: ""
}
