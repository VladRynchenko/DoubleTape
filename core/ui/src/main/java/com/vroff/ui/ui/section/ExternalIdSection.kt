package com.vroff.ui.ui.section

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vroff.domain.model.tmdb.profile.ExternalIds
import com.vroff.ui.R
import com.vroff.ui.theme.Facebook
import com.vroff.ui.theme.Instagram
import com.vroff.ui.theme.Tiktok
import com.vroff.ui.theme.X
import com.vroff.ui.theme.Youtube
import com.vroff.ui.ui.SocialMedia
import com.vroff.ui.ui.SocialMediaBaseURL

@Composable
fun ExternalIdSection(externalIds: ExternalIds) {
    val socialList =
        remember(externalIds) {
            listOfNotNull(
                externalIds.instagramId?.let {
                    SocialMedia(
                        it,
                        R.drawable.ic_instagram,
                        SocialMediaBaseURL.Instagram,
                        Instagram,
                    )
                },
                externalIds.twitterId?.let {
                    SocialMedia(
                        it,
                        R.drawable.ic_x,
                        SocialMediaBaseURL.X,
                        X,
                    )
                },
                externalIds.youtubeId?.let {
                    SocialMedia(
                        it,
                        R.drawable.ic_youtube,
                        SocialMediaBaseURL.Youtube,
                        Youtube,
                    )
                },
                externalIds.facebookId?.let {
                    SocialMedia(
                        it,
                        R.drawable.ic_facebook,
                        SocialMediaBaseURL.Facebook,
                        Facebook,
                    )
                },
                externalIds.tiktokId?.let {
                    SocialMedia(
                        it,
                        R.drawable.ic_tiktok,
                        SocialMediaBaseURL.Tiktok,
                        Tiktok,
                    )
                },
            )
        }

    val uriHandler = LocalUriHandler.current

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        socialList.forEach { social ->
            SocialIcon(social) { fullUrl ->
                uriHandler.openUri(fullUrl)
            }
        }
    }
}

@Composable
fun SocialIcon(
    platform: SocialMedia,
    onClick: (String) -> Unit,
) {
    platform.id?.let { id ->
        Icon(
            painter = painterResource(id = platform.iconRes),
            contentDescription = null,
            tint = platform.color,
            modifier =
                Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(100))
                    .clickable(
                        onClick = { onClick("${platform.baseUrl.baseURL}$id") },
                    ),
        )
    }
}

@Preview
@Composable
fun ExternalIdSectionPreview() {
    val externalIds =
        ExternalIds(
            id = 0,
            null,
            null,
            "",
            null,
            "",
            "",
            "",
            "",
            "",
            "",
        )
    ExternalIdSection(externalIds)
}
