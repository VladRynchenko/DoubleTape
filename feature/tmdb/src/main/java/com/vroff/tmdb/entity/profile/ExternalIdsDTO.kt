package com.vroff.tmdb.entity.profile

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.tmdb.profile.ExternalIds

data class ExternalIdsDTO(
    val id: Long,
    @SerializedName("freebase_mid")
    val freebaseMid: String?,
    @SerializedName("freebase_id")
    val freebaseId: String?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("tvrage_id")
    val tvrageId: Long?,
    @SerializedName("wikidata_id")
    val wikidataId: String?,
    @SerializedName("facebook_id")
    val facebookId: String?,
    @SerializedName("instagram_id")
    val instagramId: String?,
    @SerializedName("tiktok_id")
    val tiktokId: String?,
    @SerializedName("twitter_id")
    val twitterId: String?,
    @SerializedName("youtube_id")
    val youtubeId: String?,
) {
    fun mapToDomain(): ExternalIds =
        ExternalIds(
            id = id,
            freebaseMid = freebaseMid,
            freebaseId = freebaseId,
            imdbId = imdbId,
            tvrageId = tvrageId,
            wikidataId = wikidataId,
            facebookId = facebookId,
            instagramId = instagramId,
            tiktokId = tiktokId,
            twitterId = twitterId,
            youtubeId = youtubeId,
        )
}
