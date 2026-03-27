package com.vroff.domain.model.tmdb.profile

data class ExternalIds(
    val id: Long,
    val freebaseMid: String?,
    val freebaseId: String?,
    val imdbId: String?,
    val tvrageId: Long?,
    val wikidataId: String?,
    val facebookId: String?,
    val instagramId: String?,
    val tiktokId: String?,
    val twitterId: String?,
    val youtubeId: String?,
)
