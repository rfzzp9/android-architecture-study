package com.csb.koreamoviedb_mvc.view.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.csb.koreamoviedb_mvvm.tools.Filter
import com.csb.koreamoviedb_mvvm.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropdown(viewModel: MainViewModel) {
    val expanded = remember { mutableStateOf(false) }
    val options = listOf(
        Filter.FILTER_ALL,
        Filter.FILTER_TITLE,
        Filter.FILTER_ACTOR,
        Filter.FILTER_DIRECTOR
    )
    val selectedFilter = viewModel.selectedFilter.collectAsState()

    ExposedDropdownMenuBox(
        modifier = Modifier.fillMaxSize(),
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedFilter.value.text,
            onValueChange = {},
            label = { Text("검색 필터") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.text) },
                    onClick = {
                        viewModel.updateSelectedFilter(option)
                        expanded.value = false
                    }
                )
            }
        }
    }
}
