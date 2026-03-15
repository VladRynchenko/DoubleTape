package com.vroff.network

import coil3.map.Mapper
import coil3.request.Options
import coil3.size.pxOrElse
import com.vroff.domain.model.Image
import com.vroff.domain.model.ImageType
import com.vroff.domain.model.tmdb.TMDBConfiguration

class TMDBImageMapper(
    private val config: TMDBConfiguration?,
) : Mapper<Image, String> {
    override fun map(
        data: Image,
        options: Options,
    ): String? {
        val config = config ?: return null
        val widthPx = options.size.width.pxOrElse { 1080 }

        val bestSize =
            selectBestTmdbSize(
                config.specifiedSizes(data.imageType) ?: emptyList(),
                widthPx,
            )

        val cleanPath = if (data.path.startsWith("/")) data.path else "/${data.path}"

        return "${config.images?.secureBaseUrl}$bestSize$cleanPath"
    }

    private fun TMDBConfiguration.specifiedSizes(imageType: ImageType): List<String>? =
        when (imageType) {
            ImageType.BACKDROP -> {
                this.images?.backdropSizes
            }

            ImageType.LOGO -> {
                this.images?.logoSizes
            }

            ImageType.POSTER -> {
                this.images?.posterSizes
            }

            ImageType.PROFILE -> {
                this.images?.profileSizes
            }

            ImageType.STILL -> {
                this.images?.stillSizes
            }
        }

    fun selectBestTmdbSize(
        availableSizes: List<String>,
        targetWidthPx: Int,
    ): String {
        if (availableSizes.isEmpty()) return "original"
        val sizeMap =
            availableSizes
                .filter { it.startsWith("w") }
                .associateBy { it.substring(1).toIntOrNull() ?: 0 }

        val sortedWidths = sizeMap.keys.sorted()

        val bestWidth =
            sortedWidths.firstOrNull { it >= targetWidthPx }
                ?: sortedWidths.lastOrNull()

        return sizeMap[bestWidth] ?: "original"
    }
}
