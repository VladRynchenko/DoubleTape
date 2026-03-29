package com.vroff.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.vroff.domain.model.streamingavailable.ShowState
import com.vroff.domain.model.tmdb.search.MediaType
import com.vroff.domain.model.tmdb.search.typed.TypedMediaItem
import com.vroff.ui.R.string
import com.vroff.ui.ui.ErrorScreen
import com.vroff.ui.ui.LocalInnerPadding
import com.vroff.ui.ui.item.RecentSearchesItem
import com.vroff.ui.ui.item.SearchItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchQuery: TextFieldState,
    onItemClick: (Int, MediaType) -> Unit,
) {
    val viewModel: SearchViewModel = hiltViewModel()
    val pagingFlow = viewModel.pagingFlow.collectAsLazyPagingItems()
    LaunchedEffect(searchQuery.text) {
        viewModel.setSearchQuery(searchQuery.text.toString())
    }
    val screenState =
        when {
            searchQuery.text.length < 2 ->
                SearchScreenState.Waiting(viewModel.recentSearches.collectAsState().value)

            pagingFlow.loadState.refresh is LoadState.Error ->
                SearchScreenState.Error(
                    (pagingFlow.loadState.refresh as LoadState.Error)
                        .error.message
                        .orEmpty(),
                )

            pagingFlow.loadState.refresh is LoadState.NotLoading &&
                pagingFlow.loadState.append.endOfPaginationReached &&
                pagingFlow.itemCount == 0 ->
                SearchScreenState.Empty

            else ->
                SearchScreenState.Success
        }
    SearchContent(
        screenState,
        pagingFlow,
        itemClick = { id, type ->
            viewModel.addRecentSearch(searchQuery.text.toString())
            onItemClick(id, type)
        },
        {
            viewModel.addRecentSearch(it)
            searchQuery.edit { replace(0, length, it) }
        },
        onRecentSearchClearClick = viewModel::clearRecentSearches,
    )
}

@Composable
fun SearchContent(
    screenState: SearchScreenState,
    pagingFlow: LazyPagingItems<TypedMediaItem>,
    itemClick: (Int, MediaType) -> Unit,
    onRecentSearchItemClick: (String) -> Unit,
    onRecentSearchClearClick: () -> Unit,
) {
    val paddings = LocalInnerPadding.current
    AnimatedContent(
        screenState,
        transitionSpec = {
            (fadeIn(tween(220)) + slideInVertically { it / 4 }) togetherWith
                (fadeOut(tween(90)))
        },
        label = "screen_state",
    ) { state ->
        when (state) {
            is SearchScreenState.Waiting ->
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp)
                        .imePadding(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = paddings,
                ) {
                    state.list?.let {
                        if (state.list.isNotEmpty()) {
                            item {
                                Text(
                                    stringResource(R.string.clear_recent_searches),
                                    style = MaterialTheme.typography.titleSmall,
                                    textAlign = TextAlign.End,
                                    modifier =
                                        Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 12.dp)
                                            .clickable(onClick = onRecentSearchClearClick),
                                )
                            }
                            items(state.list) { query ->
                                RecentSearchesItem(
                                    modifier = Modifier.height(48.dp),
                                    searchQuery = query,
                                ) {
                                    onRecentSearchItemClick(query)
                                }
                            }
                        } else {
                            item {
                                Text(
                                    stringResource(R.string.no_recent_searches),
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 48.dp),
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }

            is SearchScreenState.Empty -> {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .imePadding(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = stringResource(string.nothing_found))
                }
            }

            is SearchScreenState.Success -> {
                val listState = rememberLazyListState()

                LazyColumn(
                    modifier =
                        Modifier
                            .padding(horizontal = 12.dp)
                            .fillMaxSize()
                            .imePadding(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = paddings,
                    state = listState,
                ) {
                    items(
                        count = pagingFlow.itemCount,
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
                ErrorScreen(
                    Modifier
                        .background(color = MaterialTheme.colorScheme.secondary)
                        .imePadding(),
                    errorText = state.e,
                )
            }

            ShowState.Waiting -> {}
            else -> {}
        }
    }
}
