package com.csb.koreamoviedb_mvc.model.data

data class Movie(
    val title: String,
    val directors: List<String>,
    val actors: List<String>,
    val plots: List<String>,
    val detailInfo: String,
)