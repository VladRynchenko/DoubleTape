package com.vroff.ui.ui.section

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vroff.ui.ui.ExpandableText

@Composable
fun OverviewSection(
    modifier: Modifier = Modifier,
    header: String,
    overview: String,
) {
    BaseSection(
        modifier,
        header = header,
        content = {
            ExpandableText(
                overview,
                minLines = 4,
            )
        },
    )
}
