package com.csb.koreamoviedb_mvvm.view.screen

import androidx.activity.ComponentActivity
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.csb.koreamoviedb_mvvm.model.data.ResultMovieClass
import com.csb.koreamoviedb_mvvm.view.component.ClickableLinkText
import com.csb.koreamoviedb_mvvm.view.component.GlideBox
import com.csb.koreamoviedb_mvvm.view.component.InfoText
import com.csb.koreamoviedb_mvvm.viewmodel.SharedMovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
) {
    val focusManager = LocalFocusManager.current

    val navBackStackEntry = remember {
        navController.previousBackStackEntry
    }
    val movie = navBackStackEntry?.savedStateHandle?.get<ResultMovieClass>("movie")

    if (movie == null) {
        Text("영화 정보를 찾을 수 없습니다.", modifier = Modifier.padding(16.dp))
        return
    }

    Scaffold(
        modifier = Modifier.pointerInput(Unit) {
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
            // 포스터
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .align(Alignment.CenterHorizontally)
            ) {
                GlideBox(
                    model = movie.posters.split("|").firstOrNull()?.trim() ?: "",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 기본 정보
            Text("기본 정보", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(12.dp))

            InfoText(label = "타이틀", value = movie.title)
            InfoText(label = "감독", value = movie.directors.joinToString().ifEmpty { "정보 없음" })
            InfoText(label = "배우", value = movie.actors.joinToString().ifEmpty { "정보 없음" })
            InfoText(label = "상영시간", value = "${movie.runtime}분")
            InfoText(label = "관람등급", value = movie.ratingGrade)
            InfoText(label = "개봉일", value = movie.releaseDate)

            Spacer(modifier = Modifier.height(24.dp))

            // 줄거리
            Text("줄거리", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = movie.plots.firstOrNull() ?: "줄거리 없음",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 상세정보 링크
            Text("상세정보", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            ClickableLinkText(url = movie.detailInfo)

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}