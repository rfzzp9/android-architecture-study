package com.csb.presentation.search.screen

import com.csb.presentation.Filter


sealed class SearchIntent {
    //검색어
    data class EnterText(val text: String) : SearchIntent()
    //필터링
    data class SelectFilter(val filter: Filter) : SearchIntent()
    //검색
    object ClickSearchButton : SearchIntent()
}