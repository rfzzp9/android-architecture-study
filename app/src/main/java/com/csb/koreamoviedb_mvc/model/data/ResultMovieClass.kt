package com.csb.koreamoviedb_mvc.model.data


data class ResultMovieClass(
    //제목
    val title: String,
    //감독들
    val directors: List<String>,
    //배우들
    val actors: List<String>,
    //줄거리(국문,영문)
    val plots: List<String>,
    //상세정보 페이지
    val detailInfo: String,
    //포스터이미지
    val posters:String,
    //대표상영시간
    val runtime:String,
    //대표관람등급
    val ratingGrade:String,
    //대표개봉일
    val releaseDate:String
)