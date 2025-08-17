package com.csb.koreamoviedb_mvvm.tools

enum class Filter (val num:Int,val text:String) {
    FILTER_ALL(0,"종합"),
    FILTER_TITLE(1,"제목"),
    FILTER_ACTOR(2,"배우"),
    FILTER_DIRECTOR(3,"감독")
}

enum class RootScreen{
    //메인화면
    SCREEN_MAIN_SCREEN,
    // 영화 상세 화면
    SCREEN_DETAIL,
}