package com.csb.koreamoviedb_mvvm.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MovieSearchResponse(
    @SerialName("Query") val query: String? = null,
    @SerialName("KMAQuery") val kmaQuery: String? = null,
    @SerialName("TotalCount") val totalCount: Int? = null,
    @SerialName("Data") val data: List<MovieData>? = null
)

@Serializable
data class MovieData(
    @SerialName("CollName") val collName: String? = null,
    @SerialName("TotalCount") val totalCount: Int? = null,
    @SerialName("Count") val count: Int? = null,
    @SerialName("Result") val result: List<MovieEntity>? = null
)

@Serializable
data class MovieEntity(
    @SerialName("DOCID") //내부 식별용
    val docId: String? = null,

    @SerialName("title") //감독명
    val title: String? = null,

    @SerialName("kmdbUrl") //상세정보 url
    val kmdbUrl: String? = null,

    @SerialName("actors") //배우정보
    val actors: Actors? = null,

    @SerialName("directors") //감독정보
    val directors: Directors? = null,

    @SerialName("plots") //줄거리
    val plots: Plots? = null,

    @SerialName("rating") //기타정보
    val rating: String? = null,

    @SerialName("runtime") //상영시간
    val runtime: String? = null,

    @SerialName("repRatDate") //대표 개봉일 - 상세
    val repRatDate: String? = null,

    @SerialName("posters") //포스터 url
    val posters: String? = null,

    @SerialName("Value")
    val value: Int? = null
)

@Serializable
data class Actors(
    @SerialName("actor")
    val actors: List<Actor>? = null
)

@Serializable
data class Actor(
    @SerialName("actorNm")
    val actorNm: String? = null
)

@Serializable
data class Directors(
    @SerialName("director")
    val directors: List<Director>? = null
)

@Serializable
data class Director(
    @SerialName("directorNm")
    val directorNm: String? = null
)

@Serializable
data class Plots(
    @SerialName("plot")
    val plot: List<Plot>? = null
)

@Serializable
data class Plot(
    @SerialName("plotText")
    val plotText: String? = null
)
