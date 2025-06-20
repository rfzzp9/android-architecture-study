package com.csb.koreamoviedb_mvc.component


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GlideBox(
    model:String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            model = model,
            contentDescription = "포스터이미지",
            contentScale = ContentScale.Fit
        )
    }
}