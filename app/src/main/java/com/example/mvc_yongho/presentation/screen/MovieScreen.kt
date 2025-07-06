package com.example.mvc_yongho.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvc_yongho.R
import com.example.mvc_yongho.domain.model.Movie
import com.example.mvc_yongho.presentation.MainIntent
import com.example.mvc_yongho.presentation.MainViewModel
import com.example.mvc_yongho.presentation.component.MovieCard
import com.example.mvc_yongho.presentation.model.MovieUiState
import com.example.mvc_yongho.presentation.theme.AndroidStudyTheme

@Composable
fun MovieScreen(
    onDetailClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val mainUiState by viewModel.uiState.collectAsState()

    MovieScreen(
        movieUiState = mainUiState,
        searchQuery = viewModel.searchQuery.collectAsState().value,
        modifier = modifier,
        onDetailClick = onDetailClick,
        onSearchClick = { title ->
            viewModel.handleIntent(MainIntent.SearchMovie(title))
        },
        onSearchQueryChange = {
            viewModel.updateSearchQuery(it)
        }
    )

}

@Composable
private fun MovieScreen(
    movieUiState: MovieUiState,
    searchQuery: String,
    onDetailClick: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                onSearchQueryChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClick(searchQuery)
                }
            )
        )
        Box(modifier = Modifier.fillMaxSize()) {
            when (movieUiState) {
                is MovieUiState.Empty -> {
                    Text(
                        text = stringResource(R.string.search_not_found),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is MovieUiState.Loading -> {
                    FullScreenLoading()
                }

                is MovieUiState.HasMovies -> {
                    LazyColumn {
                        items(movieUiState.movies.size) { index ->
                            val movie = movieUiState.movies[index]

                            MovieCard(
                                movie = movie,
                                onDetailClick = onDetailClick
                            )
                        }
                    }
                }

                is MovieUiState.Error -> {
                    ErrorScreen(message = movieUiState.message)
                }
            }
        }
    }
}

@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = message,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

class MovieScreenProvider : PreviewParameterProvider<MovieUiState> {
    override val values: Sequence<MovieUiState> = sequenceOf(
        MovieUiState.Empty,
        MovieUiState.Loading,
        MovieUiState.HasMovies(listOf(Movie.PREVIEW_SAMPLE)),
        MovieUiState.Error("에러가 발생했습니다.")
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(MovieScreenProvider::class) movieUiState: MovieUiState
) {
    AndroidStudyTheme {
        MovieScreen(
            movieUiState = movieUiState,
            searchQuery = "",
            onDetailClick = {},
            onSearchClick = {},
            onSearchQueryChange = {}
        )
    }
}
