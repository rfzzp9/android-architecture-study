package com.csb.koreamoviedb_mvvm.presentation.detail.componenet

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.csb.koreamoviedb_mvvm.util.launchBrowserChooser

@Composable
fun ClickableLinkText(
    url: String
) {
    val context = LocalContext.current
    Text(
        text = url,
        color = Color.Blue,
        //color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .clickable {
                launchBrowserChooser(context, url)
            },
        style = MaterialTheme.typography.labelMedium.copy(
            textAlign = TextAlign.Start
        )
    )
}