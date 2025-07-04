package com.csb.koreamoviedb_mvvm.presentation.search.component

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun launchBrowserChooser(context: Context, url: String) {
    try {
        val trimmedUrl = url.trim()

        if (trimmedUrl.startsWith("http://") || trimmedUrl.startsWith("https://")) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(trimmedUrl))
            val chooser = Intent.createChooser(intent, "브라우저 선택")
            context.startActivity(chooser)
        } else {
            Toast.makeText(context, "유효하지 않은 URL입니다.", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        Toast.makeText(context, "링크를 열 수 없습니다: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
    }
}
