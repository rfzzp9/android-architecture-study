package com.csb.koreamoviedb_mvp.view.screen

import android.content.Context
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import com.csb.koreamoviedb_mvp.view.component.ClickableLinkText
import com.csb.koreamoviedb_mvp.view.component.GlideBox
import com.csb.koreamoviedb_mvp.view.component.InfoText
import com.csb.koreamoviedb_mvp.view.model.SharedModel
import kotlin.collections.firstOrNull
import kotlin.collections.joinToString
import kotlin.jvm.java
import kotlin.text.orEmpty
import kotlin.text.split
import kotlin.text.trim

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val owner = context as ViewModelStoreOwner
    val viewModel = ViewModelProvider(owner)[SharedModel::class.java]
    val movie = viewModel.resultMovieClass

    val posterUrl = movie.posters.split("|").firstOrNull()?.trim()

    Scaffold(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "${movie.title} 정보") }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // 영화 포스터
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .align(Alignment.CenterHorizontally)
            ) {
                GlideBox(posterUrl.orEmpty())
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 영화 기본 정보
            Text(
                text = "기본 정보",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(12.dp))

            InfoText(label = "타이틀", value = movie.title)
            InfoText(label = "감독", value = movie.directors.joinToString())
            InfoText(label = "배우", value = movie.actors.joinToString())
            InfoText(label = "상영시간", value = "${movie.runtime}분")
            InfoText(label = "관람등급", value = movie.ratingGrade)
            InfoText(label = "개봉일", value = movie.releaseDate)

            Spacer(modifier = Modifier.height(24.dp))

            // 줄거리
            Text(
                text = "줄거리",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = movie.plots.firstOrNull() ?: "줄거리 없음", //앞이 국문줄거리임
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 상세정보
            Text(
                text = "상세정보",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            val detailUrl = movie.detailInfo.toString()
            //Log.d("detailUrl",detailUrl)
            ClickableLinkText(url = detailUrl, context = context)


            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
