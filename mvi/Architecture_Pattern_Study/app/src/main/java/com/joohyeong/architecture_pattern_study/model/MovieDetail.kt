package com.joohyeong.architecture_pattern_study.model

data class MovieDetail(
    val id: String,                   // DOCID or 내부 ID (상세 조회용, 캐싱 Key용)
    val title: String,                // 영화명
    val runtime: String,              // 대표 상영시간
    val rating: String,               // 대표 관람등급
    val posterUrl: String,            // 영화 포스터 (큰 이미지)
    val releaseDate: String,          // 대표 개봉일
    val plot: String,                 // 줄거리
    val actors: List<String>,         // 배우 이름 목록
    val directors: List<String>       // 감독 이름 목록
)