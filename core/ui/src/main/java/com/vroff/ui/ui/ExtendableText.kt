package com.vroff.ui.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    minLines: Int = 3,
    expandText: String = "Читать дальше...",
    collapseText: String = "Свернуть",
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    expandCollapseTextStyle: SpanStyle = SpanStyle(
        fontWeight = FontWeight.Bold
    )
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = text,
            style = textStyle,
            maxLines = if (expanded) Int.MAX_VALUE else minLines,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .animateContentSize(animationSpec = tween(durationMillis = 300))
                .clickable(
                    enabled = true
                ) {
                    expanded = !expanded
                }
        )

        if (!expanded && text.length > 100) {
            Text(
                text = buildAnnotatedString {
                    append("... ")
                    withStyle(expandCollapseTextStyle) {
                        append(expandText)
                    }
                },
                modifier = Modifier
                    .clickable { expanded = true }
                    .padding(top = 4.dp),
                style = textStyle
            )
        } else if (expanded && text.length > 100) {
            Text(
                text = buildAnnotatedString {
                    withStyle(expandCollapseTextStyle) {
                        append(collapseText)
                    }
                },
                modifier = Modifier
                    .clickable { expanded = false }
                    .padding(top = 4.dp),
                style = textStyle
            )
        }
    }
}