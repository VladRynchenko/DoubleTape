package com.vroff.domain.model

sealed class Image(open val path: String, val imageType: ImageType)

data class BackdropImage(override val path: String): Image(path, ImageType.BACKDROP)
data class LogoImage(override val path: String): Image(path, ImageType.LOGO)
data class PosterImage(override val path: String): Image(path, ImageType.POSTER)
data class ProfileImage(override val path: String): Image(path, ImageType.PROFILE)
data class StillImage(override val path: String): Image(path, ImageType.STILL)




enum class ImageType {
    BACKDROP, LOGO, POSTER, PROFILE, STILL
}
