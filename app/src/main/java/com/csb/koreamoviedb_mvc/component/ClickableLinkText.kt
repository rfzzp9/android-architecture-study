package com.csb.koreamoviedb_mvc.component

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.csb.koreamoviedb_mvc.tools.launchBrowserChooser

@Composable
fun ClickableLinkText(
    url: String,
    context: Context
) {
    Text(
        text = url,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .clickable {
                launchBrowserChooser(context, url)
            },
        style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start
        )
    )
}