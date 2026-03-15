package com.vroff.ui.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:parent=pixel_5",
)
@Composable
fun ShowTopAppBar(
    searchQuery: TextFieldValue = TextFieldValue(),
    onSearchQueryChange: (TextFieldValue) -> Unit = {},
    onNavigationIconClick: () -> Unit = {},
    onActionIconClick: () -> Unit = {},
    state: NavigationState = NavigationState.MainScreen,
) {
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(state) {
        keyboard?.hide()
    }

    TopAppBar(
        title = { Text("") },
        colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = Color.Transparent),
        modifier = Modifier.padding(12.dp),
        navigationIcon = {
            when (state) {
                NavigationState.More -> {
                    NavigationTopBarButton(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        onNavigationIconClick,
                    )
                }

                NavigationState.Search -> {
                    NavigationTopBarButton(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        onNavigationIconClick,
                    )
                }

                NavigationState.MainScreen -> {
                    NavigationTopBarButton(
                        Icons.Filled.AccountCircle,
                        onNavigationIconClick,
                    )
                }
            }
        },
        actions = {
            AnimatedContent(
                targetState = state,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut() using SizeTransform(clip = false)
                },
                label = "ActionsContentAnimation",
            ) { targetState ->
                when (targetState) {
                    NavigationState.Search -> {
                        NavigationTextField(
                            searchQuery,
                            onSearchQueryChange,
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
        },
    )
}

@Composable
private fun NavigationTextField(
    searchQuery: TextFieldValue,
    onSearchQueryChange: (TextFieldValue) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    SideEffect {
        focusRequester.requestFocus(FocusDirection.Exit)
    }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        placeholder = {
            Text("Enter title here")
        },
        singleLine = true,
        shape = RoundedCornerShape(100),
        colors =
            TextFieldDefaults.colors().copy(
                focusedContainerColor = MaterialTheme.colorScheme.surface.copy(0.5f),
                unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(0.5f),
            ),
        modifier =
            Modifier
                .focusRequester(focusRequester),
    )
}

@Composable
fun NavigationTopBarButton(
    icon: ImageVector,
    onClick: () -> Unit,
) = IconButton(
    onClick = onClick,
    modifier =
        Modifier
            .size(56.dp)
            .clip(CircleShape)
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSurfaceVariant.copy(0.5f),
                CircleShape,
            ).background(MaterialTheme.colorScheme.surface.copy(0.5f), CircleShape),
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
