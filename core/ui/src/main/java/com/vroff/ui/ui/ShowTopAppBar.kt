package com.vroff.ui.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vroff.ui.R
import com.vroff.ui.theme.MovieDDTheme

@Composable
fun NavigationTopBarButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    onClick: () -> Unit,
) = IconButton(
    onClick = onClick,
    modifier =
        Modifier
            .then(modifier)
            .size(56.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface.copy(0.9f), CircleShape),
) {
    Icon(
        painter = painterResource(icon),
        contentDescription = "",
        modifier = Modifier.size(32.dp),
    )
}

sealed interface TopBarState {
    data object Search : TopBarState

    data object More : TopBarState

    data object Default : TopBarState
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = false, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ShowTopAppBar(
    query: TextFieldState = TextFieldState(),
    onNavigationIconClick: () -> Unit = {},
    onActionIconClick: () -> Unit = {},
    searchFinished: (String) -> Unit = {},
    state: TopBarState = TopBarState.Search,
) {
    MovieDDTheme {
        val keyboard = LocalSoftwareKeyboardController.current
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(state) {
            if (state != TopBarState.Search) {
                keyboard?.hide()
            }
        }
        AnimatedContent(
            targetState = state,
            transitionSpec = {
                fadeIn() togetherWith fadeOut() using SizeTransform(clip = false)
            },
            label = "ActionsContentAnimation",
        ) { state ->
            when (state) {
                TopBarState.Search -> {
                    val searchBarState = rememberSearchBarState()
                    LaunchedEffect(query.text.isEmpty()) {
                        focusRequester.requestFocus()
                    }
                    SearchBarDefaults.InputField(
                        textFieldState = query,
                        onSearch = searchFinished,
                        placeholder = { Text(stringResource(R.string.searchHint)) },
                        colors =
                            TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface.copy(0.9f),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(0.9f),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            ),
                        textStyle = MaterialTheme.typography.bodyLarge,
                        searchBarState = searchBarState,
                        modifier =
                            Modifier
                                .padding(12.dp)
                                .statusBarsPadding()
                                .height(64.dp)
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                        leadingIcon = {
                            NavigationTopBarButton(
                                modifier = Modifier.padding(end = 8.dp),
                                icon = R.drawable.keyboard_arrow_left_32,
                            ) {
                                onNavigationIconClick()
                            }
                        },
                        trailingIcon = {
                            NavigationTopBarButton(
                                modifier = Modifier.padding(start = 8.dp),
                                icon = R.drawable.close_32,
                            ) {
                                query.clearText()
                            }
                        },
                    )
                }

                else -> {
                    CenterAlignedTopAppBar(
                        modifier = Modifier.padding(12.dp),
                        colors =
                            TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Transparent,
                                scrolledContainerColor = Color.Transparent,
                            ),
                        title = { },
                        navigationIcon = {
                            val icon =
                                when (state) {
                                    TopBarState.Default -> R.drawable.person_32
                                    else -> R.drawable.keyboard_arrow_left_32
                                }
                            NavigationTopBarButton(icon = icon, onClick = onNavigationIconClick)
                        },
                        actions = {
                            AnimatedContent(
                                targetState = state,
                                transitionSpec = {
                                    fadeIn() togetherWith fadeOut() using SizeTransform(clip = false)
                                },
                                label = "ActionsButtonAnimation",
                            ) { state ->
                                when (state) {
                                    TopBarState.Default -> {
                                        NavigationTopBarButton(
                                            icon = R.drawable.search_32,
                                            onClick = onActionIconClick,
                                        )
                                    }

                                    else -> {}
                                }
                            }
                        },
                    )
                }
            }
        }
    }
}
