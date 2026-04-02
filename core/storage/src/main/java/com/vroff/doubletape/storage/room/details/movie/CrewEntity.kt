package com.vroff.doubletape.storage.room.details.movie

import androidx.room.Entity
import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.movie.Crew
import com.vroff.domain.model.tmdb.search.Gender

@Entity(
    tableName = "crew",
    primaryKeys = ["id", "movieId"],
)
data class CrewEntity(
    val id: Int,
    val adult: Boolean,
    val gender: Int,
    val movieId: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val creditId: String,
    val profileImage: String?,
    val department: String,
    val job: String,
) {
    fun toDomain(): Crew =
        Crew(
            adult = adult,
            gender = Gender.entries[gender],
            id = id,
            knownForDepartment = knownForDepartment,
            name = name,
            originalName = originalName,
            popularity = popularity,
            profileImage = profileImage?.let { ProfileImage(it) },
            creditId = creditId,
            department = department,
            job = job,
        )
}

fun Crew.toEntity(movieId: Int): CrewEntity =
    CrewEntity(
        id = id,
        adult = adult,
        gender = gender.ordinal,
        movieId = movieId,
        knownForDepartment = knownForDepartment,
        name = name,
        originalName = originalName,
        popularity = popularity,
        creditId = creditId,
        profileImage = profileImage?.path,
        department = department,
        job = job,
    )
