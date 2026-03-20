package com.vroff.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
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
import com.vroff.domain.model.streamingavailable.ShowState
import com.vroff.domain.model.tmdb.search.MediaType
import com.vroff.domain.model.tmdb.search.typed.TypedSearchResult
import com.vroff.ui.ui.SearchItem

@Composable
fun SearchScreen(
    searchQuery: String,
    padding: PaddingValues,
    onItemClick: (Int, MediaType) -> Unit,
) {
    val viewModel: SearchViewModel = hiltViewModel()
    val pagingFlow = viewModel.pagingFlow.collectAsLazyPagingItems()
    LaunchedEffect(searchQuery) {
        viewModel.setSearchQuery(searchQuery)
    }
    val screenState =
        when {
            searchQuery.length < 2 ->
                SearchScreenState.Waiting

            pagingFlow.loadState.refresh is LoadState.Error ->
                SearchScreenState.Error(
                    (pagingFlow.loadState.refresh as LoadState.Error)
                        .error.message
                        .orEmpty(),
                )

            pagingFlow.loadState.append.endOfPaginationReached &&
                pagingFlow.itemCount == 0 ->
                SearchScreenState.Empty

            else ->
                SearchScreenState.Success
        }
    SearchContent(searchQuery, screenState, pagingFlow, padding, onItemClick)
}

@Composable
fun SearchContent(
    searchQuery: String,
    screenState: SearchScreenState,
    pagingFlow: LazyPagingItems<TypedSearchResult>,
    paddings: PaddingValues,
    itemClick: (Int, MediaType) -> Unit,
) {
    AnimatedContent(
        screenState,
        transitionSpec = {
            (
                fadeIn(tween(220)) +
                    slideInVertically { it / 4 }
            ) togetherWith
                (fadeOut(tween(90)))
        },
        label = "screen_state",
    ) { state ->
        when (state) {
            is SearchScreenState.Waiting ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = "Waiting")
                }

            is SearchScreenState.Empty -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = "Nothing found")
                }
            }

            is SearchScreenState.Success -> {
                val listState = rememberLazyListState()
                LaunchedEffect(searchQuery) {
                    listState.scrollToItem(0)
                }
                LazyColumn(
                    modifier =
                        Modifier
                            .padding(horizontal = 12.dp)
                            .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = paddings,
                    state = listState,
                ) {
                    items(
                        count = pagingFlow.itemCount,
                        key = pagingFlow.itemKey { item -> "${item.mediaType.name}/${item.id}" },
                    ) { index ->
                        pagingFlow[index]?.let {
                            SearchItem(
                                item = it,
                                onItemClick = itemClick,
                            )
                        }
                    }
                }
            }

            is SearchScreenState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(state.e, color = MaterialTheme.colorScheme.error)
                }
            }

            ShowState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            ShowState.Waiting -> {}
            else -> {}
        }
    }
}
