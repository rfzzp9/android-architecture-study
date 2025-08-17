package com.csb.koreamoviedb_mvvm.presentation.search

import com.csb.koreamoviedb_mvvm.Filter

sealed class SearchIntent {
    //검색어
    data class EnterText(val text: String) : SearchIntent()
    //필터링
    data class SelectFilter(val filter: Filter) : SearchIntent()
    //검색
    object ClickSearchButton : SearchIntent()
}