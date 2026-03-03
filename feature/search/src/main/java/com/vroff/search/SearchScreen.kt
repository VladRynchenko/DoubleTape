package com.vroff.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.vroff.domain.models.ShowState
import com.vroff.ui.ui.SearchMovieCard

@Composable
fun SearchScreen(
    searchQuery: String,
    paddings: PaddingValues,
    itemClick: (String) -> Unit
) {
    val viewModel: SearchViewModel = hiltViewModel()
    val screenState = viewModel.stateFlow.collectAsState()
    LaunchedEffect(searchQuery) {
        viewModel.setSearchQuery(searchQuery)
    }
    SearchContent(screenState.value, paddings, itemClick)
}

@Composable
fun SearchContent(
    screenState: ShowState = ShowState.Loading,
    paddings: PaddingValues,
    itemClick: (String) -> Unit
) {
    when (screenState) {
        is ShowState.Success -> {
            val listState = rememberLazyListState()

            LaunchedEffect(screenState.showList) {
                listState.animateScrollToItem(0)
            }

            if (screenState.showList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Empty")
                }
            } else
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = paddings,
                    state = listState
                ) {

                    items(screenState.showList, key = { it.tmdbId }) { show ->
                        SearchMovieCard(
                            item = show,
                            onItemClick = itemClick
                        )
                    }
                }
        }

        is ShowState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(screenState.error ?: "", color = MaterialTheme.colorScheme.error)
            }
        }

        ShowState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        ShowState.Waiting -> {}
    }

}