package com.vroff.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.vroff.domain.model.streaming_available.ShowState
import com.vroff.domain.model.tmdb.search.MediaType
import com.vroff.domain.model.tmdb.search.typed_result.TypedSearchResult
import com.vroff.ui.ui.SearchItem

@Composable
fun SearchScreen(
    searchQuery: String,
    padding: PaddingValues,
    onItemClick: (Int, MediaType) -> Unit
) {
    val viewModel: SearchViewModel = hiltViewModel()
    val pagingFlow = viewModel.pagingFlow.collectAsLazyPagingItems()
    LaunchedEffect(searchQuery) {
        viewModel.setSearchQuery(searchQuery)
    }
    val screenState = when {
        searchQuery.length < 2 -> SearchScreenState.Waiting

        pagingFlow.loadState.refresh is LoadState.Loading ->
            SearchScreenState.Loading

        pagingFlow.loadState.refresh is LoadState.Error ->
            SearchScreenState.Error((pagingFlow.loadState.refresh as LoadState.Error).error.message.toString())

        else ->
            SearchScreenState.Success
    }
    SearchContent(screenState, pagingFlow, padding, onItemClick)

}

@Composable
fun SearchContent(
    screenState: SearchScreenState,
    pagingFlow: LazyPagingItems<TypedSearchResult>,
    paddings: PaddingValues,
    itemClick: (Int, MediaType) -> Unit
) {

    when (screenState) {
        is SearchScreenState.Success -> {
            val listState = rememberLazyListState()

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = paddings,
                state = listState
            ) {
                items(
                    count = pagingFlow.itemCount,
                    key = pagingFlow.itemKey { item -> item.id }) { index ->
                    pagingFlow[index]?.let {
                        SearchItem(
                            item = it,
                            onItemClick = itemClick
                        )
                    }
                }
            }
        }

        is SearchScreenState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(screenState.e, color = MaterialTheme.colorScheme.error)
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
        else -> {}
    }

}