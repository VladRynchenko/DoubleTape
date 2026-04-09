package com.vroff.ui.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vroff.ui.R

@Preview
@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    errorText: String = stringResource(R.string.something_went_wrong),
    colorText: Color = MaterialTheme.colorScheme.error,
) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center,
    ) {
        Text(errorText, color = colorText)
    }
}

@Preview
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}
