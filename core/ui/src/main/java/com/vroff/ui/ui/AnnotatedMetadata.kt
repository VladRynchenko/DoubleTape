package com.vroff.ui.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.vroff.domain.model.constants.SymbolConstant

@Composable
fun AnnotatedMetadata(
    label: String,
    value: String?,
    modifier: Modifier = Modifier,
) {
    val annotatedString =
        remember(label, value) {
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) { append("$label: ") }
                append(value ?: SymbolConstant.HYPHEN)
            }
        }
    Text(text = annotatedString, style = MaterialTheme.typography.bodyLarge, modifier = modifier.fillMaxWidth())
}
