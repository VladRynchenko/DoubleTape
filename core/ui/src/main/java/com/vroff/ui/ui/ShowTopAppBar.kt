package com.vroff.ui.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NavigationTopBarButton(
    icon: ImageVector,
    onClick: () -> Unit,
) = IconButton(
    onClick = onClick,
    modifier =
        Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface.copy(0.9f), CircleShape),
) {
    Icon(
        imageVector = icon,
        contentDescription = "",
        modifier = Modifier.size(32.dp),
    )
}

sealed class NavigationState {
    data object Search : NavigationState()

    data object More : NavigationState()

    data object MainScreen : NavigationState()
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShowTopAppBarTest(
    searchQuery: TextFieldState = TextFieldState(),
    searchHint: String = "Search",
    onNavigationIconClick: () -> Unit = {},
    onActionIconClick: () -> Unit = {},
    searchFinished: () -> Unit = {},
    state: NavigationState = NavigationState.Search,
) {
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(state) {
        if (state != NavigationState.Search) {
            keyboard?.hide()
        }
    }

    TopAppBar(
        modifier = Modifier.padding(12.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
        ),
        actions = { },
        navigationIcon = {
            val icon = when (state) {
                NavigationState.MainScreen -> Icons.Filled.AccountCircle
                else -> Icons.AutoMirrored.Filled.ArrowBack
            }
            NavigationTopBarButton(icon, onNavigationIconClick)
        },
        title = {
            AnimatedContent(
                targetState = state,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut() using SizeTransform(clip = false)
                },
                label = "ActionsContentAnimation",
                modifier = Modifier.offset(3.dp)
            ) { state ->
                Box(
                    Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    when (state) {
                        NavigationState.Search -> {
                            CustomNavigationSearchBar(
                                searchHint = searchHint,
                                searchQuery = searchQuery,
                                onSearch = {
                                    keyboard?.hide()
                                    searchFinished()
                                },
                            )
                        }

                        NavigationState.MainScreen -> {
                            NavigationTopBarButton(
                                Icons.Filled.Search,
                                onActionIconClick,
                            )
                        }

                        NavigationState.More -> {
                            NavigationTopBarButton(
                                Icons.Default.MoreVert,
                                onActionIconClick,
                            )
                        }
                    }
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomNavigationSearchBar(
    searchHint: String,
    searchQuery: TextFieldState,
    onSearch: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    SearchBarDefaults.InputField(
        state = searchQuery,
        onSearch = {
            keyboard?.hide()
            onSearch.invoke(it)
        },
        expanded = false,
        onExpandedChange = {},
        placeholder = { Text(searchHint) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface.copy(0.9f),
            unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(0.9f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            ),
        textStyle = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .focusRequester(focusRequester)
            .height(64.dp),
    )
}
