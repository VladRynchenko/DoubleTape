package com.vroff.ui.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WideButton(
    modifier: Modifier,
    icon: Icon,
    contentDescription: String? = null,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        shape = CircleShape,
        onClick = onClick,
        colors =
            IconButtonDefaults.iconButtonColors().copy(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
    ) {
        Icon(
            painter = painterResource(icon.icon),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = contentDescription ?: icon.contentDescription,
        )
    }
}

@Preview
@Composable
fun WideButtonPreview() {
    WideButton(
        modifier = Modifier.fillMaxWidth(),
        icon = Icon.Play,
        contentDescription = "Play",
        onClick = {},
    )
}
