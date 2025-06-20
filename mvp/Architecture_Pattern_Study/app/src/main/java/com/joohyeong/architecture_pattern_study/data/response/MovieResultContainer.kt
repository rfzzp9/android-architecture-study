package com.joohyeong.architecture_pattern_study.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResultContainer(
    @SerialName("Data") val data: List<MovieResultGroup>
)

@Serializable
data class MovieResultGroup(
    @SerialName("Result") val result: List<MovieEntity>
)

@Serializable
data class MovieEntity(
    @SerialName("DOCID") val id: String,                         // 내부 식별자 (캐싱/상세조회용) - 공통
    val title: String,                                           // 영화명 - 공통
    val runtime: String,                                         // 대표 상영시간 - 공통
    val rating: String,                                          // 대표 관람등급 - 공통
    val posters: String,                                         // 영화 포스터 이미지 - 공통 (리스트: 작게, 상세: 크게)
    @SerialName("repRlsDate") val releaseDate: String,           // 대표 개봉일 - 상세
    val plots: Plots,                                            // 줄거리 - 상세
    val actors: Actors,                                          // 배우명 - 상세
    val directors: Directors                                     // 감독명 - 상세
)

@Serializable
data class Plots(
    val plot: List<Plot>
)

@Serializable
data class Plot(
    val plotText: String  // 줄거리 내용 - 상세
)

@Serializable
data class Actors(
    val actor: List<Actor>
)

@Serializable
data class Actor(
    val actorNm: String  // 배우명 - 상세
)

@Serializable
data class Directors(
    val director: List<Director>
)

@Serializable
data class Director(
    val directorNm: String // 감독명 - 상세
)