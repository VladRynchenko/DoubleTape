package com.vroff.ui.model

import androidx.compose.ui.graphics.Color

data class SocialMedia(
    val id: String?,
    val iconRes: Int,
    val baseUrl: SocialMediaBaseURL,
    val color: Color,
)

enum class SocialMediaBaseURL(
    val baseURL: String,
) {
    Instagram("https://instagram.com/"),
    X("https://x.com/"),
    Youtube("https://youtube.com/"),
    Facebook("https://facebook.com/"),
    Tiktok("https://www.tiktok.com/@"),
}
