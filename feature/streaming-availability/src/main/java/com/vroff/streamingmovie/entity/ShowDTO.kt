package com.vroff.streamingmovie.entity

import com.google.gson.annotations.SerializedName

data class ShowDTO(
    @SerializedName("id") var id: String,
    @SerializedName("showType") var showType: String,
    @SerializedName("imdbId") var imdbId: String,
    @SerializedName("tmdbId") var tmdbId: String,
    @SerializedName("title") var title: String,
    @SerializedName("overview") var overview: String,
    @SerializedName("releaseYear") var releaseYear: Int? = null,
    @SerializedName("firstAirYear") var firstAirYear: Int? = null,
    @SerializedName("lastAirYear") var lastAirYear: Int? = null,
    @SerializedName("originalTitle") var originalTitle: String,
    @SerializedName("genres") var genres: ArrayList<GenreDTO>,
    @SerializedName("directors") var directors: ArrayList<String>?,
    @SerializedName("cast") var cast: ArrayList<String>,
    @SerializedName("rating") var rating: Int,
    @SerializedName("seasonCount") var seasonCount: Int? = null,
    @SerializedName("episodeCount") var episodeCount: Int? = null,
    @SerializedName("runtime") var runtime: Int?,
    @SerializedName("imageSet") var imageSet: ImageSetDTO,
    @SerializedName("creators") var creators: ArrayList<String>?,
    @SerializedName("seasons") var seasons: ArrayList<SeasonDTO>?

)