package com.vroff.ui.ui.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BaseSection(
    modifier: Modifier = Modifier,
    header: String,
    content: @Composable () -> Unit,
) {
    Column(modifier) {
        Text(
            header,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 12.dp),
        )
        content.invoke()
    }
}
