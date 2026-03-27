package com.vroff.ui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.vroff.ui.R

@Composable
fun HeaderText(
    modifier: Modifier = Modifier,
    headerText: String,
    additionText: String = stringResource(R.string.see_more),
    isSeeMore: Boolean = false,
    icon: Painter? = null,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    onMoreClicked: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            modifier
                .fillMaxWidth(),
    ) {
        Text(
            headerText,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = textColor,
        )
        if (isSeeMore) {
            SeeMoreText(
                text = additionText,
                icon = icon,
                onClick = onMoreClicked,
            )
        }
    }
}
