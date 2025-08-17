package com.example.mvc_yongho.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.mvc_yongho.R
import com.example.mvc_yongho.domain.model.Movie
import com.example.mvc_yongho.presentation.theme.AndroidStudyTheme

@Composable
fun MovieCard(
    movie: Movie,
    onDetailClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier.padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.posterUrl)
                        .crossfade(true)
                        .error(R.drawable.no_image)
                        .fallback(R.drawable.no_image)
                        .build(),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .size(width = 80.dp, height = 120.dp),
                    contentScale = ContentScale.Crop,
                    loading = {
                        CircularProgressIndicator()
                    }
                )

                Text(
                    text = movie.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp, top = 10.dp)
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )

            MovieInfoSection(movie = movie)

            Text(
                text = movie.plotText,
                lineHeight = 24.sp,
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            Button(
                onClick = { onDetailClick(movie.kmdbUrl) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(text = stringResource(R.string.view_details_on_kmdb))
            }
        }
    }
}

@Composable
private fun MovieInfoSection(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MovieInfoRow(
            leftLabel = stringResource(R.string.label_director),
            leftValue = movie.directorName,
            rightLabel = stringResource(R.string.label_year),
            rightValue = movie.prodYear
        )

        MovieInfoRow(
            leftLabel = stringResource(R.string.label_nation),
            leftValue = movie.nation,
            rightLabel = stringResource(R.string.label_runtime),
            rightValue = movie.runtimeWithMinutes
        )
    }
}

@Composable
private fun MovieInfoRow(
    leftLabel: String,
    leftValue: String,
    rightLabel: String,
    rightValue: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$leftLabel: $leftValue",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "$rightLabel: $rightValue",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview
@Composable
private fun MovieCardPreview() {
    AndroidStudyTheme {
        MovieCard(movie = Movie.PREVIEW_SAMPLE, onDetailClick = { })
    }
}
