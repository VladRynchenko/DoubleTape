package com.vroff.ui.ui.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vroff.domain.model.BackdropImage
import com.vroff.domain.model.constants.SymbolConstant
import com.vroff.ui.ui.BackdropImageWidget
import com.vroff.ui.ui.RatingWidget

@Composable
fun BackdropItem(
    modifier: Modifier,
    image: BackdropImage?,
    title: String,
    date: String?,
    rating: Float?,
    subtitle: String?,
    onItemClicked: () -> Unit,
) {
    BackdropImageWidget(
        modifier =
            modifier
                .clickable(onClick = onItemClicked),
        image = image,
        content = {
            Column {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(12.dp),
                )
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp),
                ) {
                    date?.takeIf { it != SymbolConstant.EMPTY }?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    if (date != null && rating != null) {
                        Text(
                            SymbolConstant.MIDDLE_POINT,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    }

                    rating?.let {
                        RatingWidget(it)
                    }
                }

                subtitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        },
    )
}
