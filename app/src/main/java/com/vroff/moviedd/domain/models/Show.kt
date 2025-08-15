package com.vroff.moviedd.domain.models

data class Show(
    var id: String,
    var showType: ShowType,
    var imdbId: String,
    var tmdbId: String,
    var title: String,
    var overview: String,
    var releaseYear: Int? = null,
    var firstAirYear: Int? = null,
    var lastAirYear: Int? = null,
    var originalTitle: String,
    var genres: List<Genre>,
    var directors: List<String>?,
    var cast: List<String>,
    var rating: Int,
    var seasonCount: Int? = null,
    var episodeCount: Int? = null,
    var runtime: Int?,
    var imageSet: ImageSet,
    var creators: List<String>?,
    var seasons: List<Season>?
){

    fun getReleaseData(): String {
        return when (this.showType) {
            ShowType.MOVIE -> {
                this.releaseYear.toString()
            }

            ShowType.SERIES -> {
                if (this.firstAirYear == this.lastAirYear) {
                    "${this.firstAirYear}"
                } else {
                    "${this.firstAirYear} - ${this.lastAirYear}"
                }
            }
        }
    }
}