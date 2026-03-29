package com.vroff.ui.ui.widget

import com.vroff.ui.R

sealed class Icon(
    val icon: Int,
    val contentDescription: String,
) {
    object Play : Icon(R.drawable.play, "Play")
}
