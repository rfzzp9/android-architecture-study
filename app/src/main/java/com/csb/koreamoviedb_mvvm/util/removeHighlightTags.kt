package com.csb.koreamoviedb_mvvm.util

//하이라이팅태그제거
//<!HS>: Highlight Start
//
//<!HE>: Highlight End
fun removeHighlightTags(input: String): String {
    return input.replace("<!HS>", "").replace("<!HE>", "")
}