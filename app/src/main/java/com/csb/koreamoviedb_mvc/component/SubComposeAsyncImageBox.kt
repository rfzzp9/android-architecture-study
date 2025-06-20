package com.csb.koreamoviedb_mvc.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import com.csb.koreamoviedb_mvc.tools.rememberDefaultShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun SubComposeAsyncImageBox(
    model:String
){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
            SubcomposeAsyncImage(
                model = model,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit,
            ) {
                when{
                    painter.state is AsyncImagePainter.State.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        ){
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .shimmer(rememberDefaultShimmer())
                                    .background(
                                        Color.LightGray.copy(alpha = 0.7f),
                                        RoundedCornerShape(4.dp)
                                    )
                            )
                        }
                    }

                    painter.state is AsyncImagePainter.State.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 20.dp, end = 20.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    Color.LightGray.copy(alpha = 0.7f),
                                    RoundedCornerShape(4.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "이미지 로딩 에러", color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    else -> {
                        Image(
                            painter = painter,
                            contentDescription = contentDescription,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 20.dp, end = 20.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }

    }
}
