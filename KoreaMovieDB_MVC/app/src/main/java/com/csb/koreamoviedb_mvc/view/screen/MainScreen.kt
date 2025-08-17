package com.csb.koreamoviedb_mvc.view.screen

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.csb.koreamoviedb_mvc.view.component.CustomProgressDialog
import com.csb.koreamoviedb_mvc.view.component.FilterDropdown
import com.csb.koreamoviedb_mvc.view.component.MovieItem
import com.csb.koreamoviedb_mvc.controller.MovieController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    NavController: NavHostController,
    context: Context,
    controller: MovieController
) {

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

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
        CustomProgressDialog(isShowing = controller.progressDialogIsShowing.value)

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
                        FilterDropdown(controller)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        singleLine = true,
                        value = controller.searchText.value,
                        onValueChange = { controller.updateSearchText(it) },
                        label = { Text("입력") },
                        modifier = Modifier.weight(2f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = {
                        if (controller.searchText.value.isNotBlank()) {
                            controller.search()
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

            // 결과 리스트
            if (controller.resultList.isNotEmpty()) {
                items(controller.resultList) { movie ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        MovieItem(NavController,context,movie = movie)
                    }
                }
            } else if (controller.hasSearched.value) {
                item {
                    Text("결과 없음")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen() {
//    val movieController = MovieController(this,MovieModel())
//    SearchScreen(movieController)
}

