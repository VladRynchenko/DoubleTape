package com.vroff.moviedd.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HideableItem(
    modifier: Modifier = Modifier,
    initialExtended: Boolean = false,
    title: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    var extended by rememberSaveable { mutableStateOf(initialExtended) }
    val indicationSource = remember { MutableInteractionSource() }
    val rotationAngle by animateFloatAsState(
        targetValue = if (extended) 90f else 0f,
        animationSpec = tween(durationMillis = 300), label = "ArrowRotation"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = indicationSource,
                indication = null,
                true,
            ) {
                extended = !extended
            },
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = if (extended) "Свернуть" else "Развернуть",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.graphicsLayer(rotationZ = rotationAngle)
            )

            title.invoke()
        }
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 12.dp),
            visible = extended,
            enter = expandVertically(animationSpec = tween(300)) + fadeIn(animationSpec = tween(300)),
            exit = shrinkVertically(animationSpec = tween(300)) + fadeOut(animationSpec = tween(300))
        ) {
            content.invoke()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewHideableItem() {

    Surface(Modifier.padding(vertical = 40.dp)) {
        HideableItem(
            title = {
                Text("Title")
            },
            initialExtended = true
        ) {
            Column() {
                repeat(7) {
                    Text("Episode $it")
                }
            }
        }
    }
}