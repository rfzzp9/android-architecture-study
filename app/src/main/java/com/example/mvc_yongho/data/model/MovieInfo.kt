package com.example.mvc_yongho.data.model

import com.example.mvc_yongho.domain.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieInfo(
    @SerialName("DOCID") val docId: String,
    val movieId: String,
    val movieSeq: String,
    val title: String,
    val titleEng: String,
    val titleOrg: String,
    val titleEtc: String,
    val prodYear: String,
    val directors: Directors,
    val actors: Actors,
    val nation: String,
    val company: String,
    val plots: Plots,
    val runtime: String,
    val rating: String,
    val genre: String,
    val kmdbUrl: String,
    val posters: String
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

fun MovieInfo.toMovie(): Movie {
    return Movie(
        title = cleanTitle,
        posterUrl = posterUrl,
        directorName = directorName,
        prodYear = prodYear,
        nation = nation,
        runtimeWithMinutes = runtimeWithMinutes,
        plotText = plotText,
        kmdbUrl = kmdbUrl
    )
}
