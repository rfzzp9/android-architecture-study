package com.csb.koreamoviedb_mvp.view.screen

import android.content.Context
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.csb.koreamoviedb_mvp.model.data.ResultMovieClass
import com.csb.koreamoviedb_mvp.presenter.MoviePresenter
import com.csb.koreamoviedb_mvp.tools.Filter
import com.csb.koreamoviedb_mvp.view._interface.SearchView
import com.csb.koreamoviedb_mvp.view.component.CustomProgressDialog
import com.csb.koreamoviedb_mvp.view.component.FilterDropdown
import com.csb.koreamoviedb_mvp.view.component.MovieItem
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    presenter: MoviePresenter,
    //listState: LazyListState
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val searchText = remember { mutableStateOf("") }
    val selectedFilter = remember { mutableStateOf(Filter.FILTER_ALL) }
    val isLoading = remember { mutableStateOf(false) }
    val resultList = remember { mutableStateOf<List<ResultMovieClass>>(emptyList()) }
    val hasSearched = remember { mutableStateOf(false) }
    val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }

    //인터페이스 오버라이딩
    val view = object : SearchView {
        override fun showLoading() {
            isLoading.value = true
        }

        override fun hideLoading() {
            isLoading.value = false
        }

        override fun showResults(results: List<ResultMovieClass>) {
            resultList.value = results
            hasSearched.value = true
        }

        override fun showEmptyMessage() {
            resultList.value = emptyList()
            hasSearched.value = true
        }

        override fun restoreSearchInput(search: String, filter: Filter) {
            searchText.value = search
            selectedFilter.value = filter
        }
    }

    LaunchedEffect(Unit) {
        presenter.attachView(view)
    }

    DisposableEffect(Unit) {
        onDispose {
            presenter.detachView()
        }
    }

    LaunchedEffect(Unit) {
        println("첫 아이템 위치: ${listState.firstVisibleItemIndex}")
    }

    Scaffold(
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = { focusManager.clearFocus() })
        },
        topBar = {
            CenterAlignedTopAppBar(title = { Text("한국영상자료원") })
        }
    ) { innerPadding ->
        CustomProgressDialog(isShowing = isLoading.value)

        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        FilterDropdown(
                            selectedFilter = selectedFilter.value,
                            onFilterSelected = { selectedFilter.value = it }
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        singleLine = true,
                        value = searchText.value,
                        onValueChange = { searchText.value = it },
                        label = { Text("입력") },
                        modifier = Modifier.weight(2f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = {
                        if (searchText.value.isNotBlank()) {
                            presenter.search(searchText.value, selectedFilter.value)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .wrapContentWidth(Alignment.End),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text("검색", color = Color.Black)
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            val itemsToShow = resultList.value

            if (itemsToShow.isEmpty() && hasSearched.value) {
                item {
                    Text("결과 없음")
                }
            } else {
                items(items = itemsToShow) { movie ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        MovieItem(navController, context, movie = movie)
                    }
                }
            }
        }
    }
}