package com.csb.koreamoviedb_mvc.view.component

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import com.csb.koreamoviedb_mvc.model.data.ResultMovieClass
import com.csb.koreamoviedb_mvc.tools.RootScreen
import com.csb.koreamoviedb_mvc.view.model.SharedModel

@Composable
fun MovieItem(
    NavController: NavHostController,
    context: Context,
    movie: ResultMovieClass) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable{
                val owner = context as ViewModelStoreOwner
                val viewModel = ViewModelProvider(owner)[SharedModel::class.java]
                viewModel.setData(movie)
                NavController.navigate(RootScreen.SCREEN_DETAIL.name)
            },
        //verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .align(Alignment.CenterVertically),
        ){
            //val posterUrl = movie.posters?.split("|")?.firstOrNull()
            Log.d("movie.posters",movie.posters.toString())
            val posterUrl = movie.posters?.split("|")?.firstOrNull()?.trim() //트림트림트림
            Log.d("movie.posters",posterUrl.toString())

            //SubComposeAsyncImageBox(posterUrl.toString())
            GlideBox(posterUrl.toString())
        }
        Spacer(modifier = Modifier.width(5.dp))
        Column(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "상영시간: ${movie.runtime}분",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "관람등급: ${movie.ratingGrade}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMovieItem() {
    val dummyMovie = ResultMovieClass(
        title = "헌트",
        directors = listOf("이정재"),
        actors = listOf("이정재", "정우성", "전혜진", "허성태", "김종수"),
        plots = listOf("1980년대, 서로를 의심하는 안기부 요원들의 첩보전...", "Espionage drama set in the 1980s."),
        detailInfo = "https://movie.daum.net/moviedb/main?movieId=123456",
        posters = "http://file.koreafilm.or.kr/thm/02/00/03/51/tn_DPF05297A.jpg",
        runtime = "125분",
        ratingGrade = "15세이상관람가",
        releaseDate = "2022-08-10"
    )

    //MovieItem(movie = dummyMovie)
}
