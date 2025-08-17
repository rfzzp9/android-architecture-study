package com.csb.koreamoviedb_mvvm.presentation.search

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.csb.koreamoviedb_mvvm.presentation.search.component.FilterDropdown
import com.csb.koreamoviedb_mvvm.presentation.search.SearchIntent
import com.csb.koreamoviedb_mvvm.presentation.search.component.MovieItem
import com.csb.koreamoviedb_mvvm.presentation.search.MainViewModel
import com.csb.koreamoviedb_mvvm.presentation.search.component.CustomProgressDialog

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current

    val uiState by viewModel.uiState.collectAsState()


    Scaffold(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "한국영상자료원") }
            )
        }
    ) { innerPadding ->
        CustomProgressDialog(isShowing = uiState.isLoading)
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        FilterDropdown(
                            selectedFilter = uiState.selectedFilter,
                            onFilterSelected = { viewModel.processIntent(SearchIntent.SelectFilter(it)) }
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        singleLine = true,
                        value = uiState.searchText,
                        onValueChange = { viewModel.processIntent(SearchIntent.EnterText(it)) },
                        label = { Text("입력") },
                        modifier = Modifier.weight(2f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = {
                        if (uiState.searchText.isNotBlank()) {
                            viewModel.processIntent(SearchIntent.ClickSearchButton)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text("검색", color = Color.Black)
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            if (uiState.resultList.isNotEmpty()) {
                itemsIndexed(uiState.resultList) { index, movie ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        MovieItem(
                            navController = navHostController,
                            movie = movie
                        )
                    }
                }
            } else if (uiState.hasSearched) {
                item {
                    Text("결과 없음", modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}