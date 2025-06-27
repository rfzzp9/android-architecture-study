package com.example.mvc_yongho.domain.model

data class Movie(
    val title: String,
    val posterUrl: String,
    val directorName: String,
    val prodYear: String,
    val nation: String,
    val runtimeWithMinutes: String,
    val plotText: String,
    val kmdbUrl: String
) {
    companion object {
        val PREVIEW_SAMPLE = Movie(
            title = "아바타",
            posterUrl = "http://file.koreafilm.or.kr/thm/02/00/01/36/tn_DPF001624.jpg",
            directorName = "제임스 카메론",
            prodYear = "2009",
            nation = "미국",
            runtimeWithMinutes = "162분",
            plotText = "신비로운 생명체와 아름다운 자연을 자랑하는 행성, 판도라...",
            kmdbUrl = "https://www.kmdb.or.kr/db/kor/detail/movie/F/24464"
        )
    }
}
