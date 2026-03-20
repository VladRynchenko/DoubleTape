package com.vroff.ui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vroff.ui.FormatConstant
import com.vroff.ui.R
import com.vroff.ui.theme.MovieDDTheme
import java.util.Locale

@Composable
fun RatingWidget(
    rating: Float,
    modifier: Modifier = Modifier,
) {
    var textHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
    ) {
        Icon(
            modifier = Modifier.size(textHeight),
            painter = painterResource(id = R.drawable.star_32),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary,
        )

        Text(
            FormatConstant.ROUND_ONE_DIGIT.format(Locale.ROOT, rating),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSecondary,
            onTextLayout = { textLayoutResult ->
                textHeight =
                    with(density) {
                        textLayoutResult.size.height.toDp()
                    }
            },
        )
    }
}

@Preview
@Composable
fun RatingPreview() {
    MovieDDTheme {
        RatingWidget(4.532f)
    }
}
