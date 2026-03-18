package com.vroff.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.common.Cast
import com.vroff.domain.model.tmdb.common.CastBase
import com.vroff.domain.model.tmdb.common.SeriesCast
import com.vroff.domain.model.tmdb.search.Gender
import com.vroff.domain.model.tmdb.series.Role

class CastItemPreviewProvider : PreviewParameterProvider<CastBase> {
    override val values: Sequence<CastBase>
        get() = sequenceOf(movieCast, seriesCast)
}

val movieCast =
    Cast(
        adult = false,
        gender = Gender.entries[2],
        id = 13240,
        knownForDepartment = "Acting",
        name = "Mark Wahlberg",
        originalName = "Mark Wahlberg",
        popularity = 7.7172,
        profileImage = ProfileImage("/bTEFpaWd7A6AZVWOqKKBWzKEUe8.jpg"),
        castId = 1,
        character = "Dan Morgan",
        creditId = "66f7ebf8f5d56e9ed8a209b0",
        order = 0,
    )

val seriesCast =
    SeriesCast(
        adult = false,
        gender = Gender.entries[2],
        id = 51797,
        knownForDepartment = "Acting",
        name = "Nathan Fillion",
        originalName = "Nathan Fillion",
        popularity = 6.7684,
        profileImage = ProfileImage("/q31mXXgnN5PsuIjEqaaAPvBDvHc.jpg"),
        roles = listOf(Role(creditId = "5b12f9100e0a263dd9007baf", character = "John Nolan", episodeCount = 139)),
        totalEpisodeCount = 139,
        order = 0,
    )
