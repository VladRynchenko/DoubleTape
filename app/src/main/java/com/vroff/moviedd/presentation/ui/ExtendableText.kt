package com.vroff.moviedd.presentation.ui

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

/**
 * Расширяемый текстовый компонент, который показывает ограниченное количество строк
 * и позволяет пользователю "читать дальше" для просмотра полного текста.
 *
 * @param text Полный текст для отображения.
 * @param modifier Модификатор для настройки внешнего вида компонента.
 * @param minLines Минимальное количество строк, отображаемых в свернутом состоянии.
 * @param expandText Текст для ссылки "Читать дальше".
 * @param collapseText Текст для ссылки "Свернуть".
 * @param textStyle Стиль текста для основного содержимого.
 * @param expandCollapseTextStyle Стиль текста для ссылок "Читать дальше" / "Свернуть".
 */
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
        // Text composable с animateContentSize для плавной анимации
        Text(
            text = text,
            style = textStyle,
            maxLines = if (expanded) Int.MAX_VALUE else minLines,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .animateContentSize(animationSpec = tween(durationMillis = 300)) // Плавная анимация
                .clickable(
                    enabled = true // Всегда кликабельно для расширения/сворачивания
                ) {
                    expanded = !expanded
                }
        )

        if (!expanded && text.length > 100) { // Пример условия: если текст длинный
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
                style = textStyle // Применяем общий стиль, но SpanStyle переопределит цвет
            )
        } else if (expanded && text.length > 100) { // Показываем "Свернуть", если текст был длинный
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