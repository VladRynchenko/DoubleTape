package com.vroff.doubletape.storage.room.details.series

import androidx.room.Relation
import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.common.SeriesCast
import com.vroff.domain.model.tmdb.search.Gender
import com.vroff.domain.model.tmdb.series.AggregateCredits
import com.vroff.domain.model.tmdb.series.Job
import com.vroff.domain.model.tmdb.series.Role
import com.vroff.domain.model.tmdb.series.SeriesCrew
import com.vroff.doubletape.storage.room.details.series.entity.SeriesCastEntity
import com.vroff.doubletape.storage.room.details.series.entity.SeriesCastWithRoles
import com.vroff.doubletape.storage.room.details.series.entity.SeriesCrewEntity
import com.vroff.doubletape.storage.room.details.series.entity.SeriesCrewWithJobs

data class AggregatedCreditsWithDetail(
    val id: Int,
    @Relation(
        entity = SeriesCastEntity::class,
        parentColumn = "id",
        entityColumn = "seriesId",
    )
    val cast: List<SeriesCastWithRoles>,
    @Relation(
        entity = SeriesCrewEntity::class,
        parentColumn = "id",
        entityColumn = "seriesId",
    )
    val crew: List<SeriesCrewWithJobs>,
) {
    fun toDomain(): AggregateCredits =
        AggregateCredits(
            cast =
                cast.map { (cast, roles) ->
                    SeriesCast(
                        adult = cast.adult,
                        gender = Gender.entries[cast.gender],
                        id = cast.id,
                        knownForDepartment = cast.knownForDepartment,
                        name = cast.name,
                        originalName = cast.originalName,
                        popularity = cast.popularity,
                        profileImage = cast.profileImage?.let { ProfileImage(it) },
                        roles =
                            roles.map { role ->
                                Role(
                                    role.creditId,
                                    role.character,
                                    role.episodeCount,
                                )
                            },
                        totalEpisodeCount = cast.totalEpisodeCount,
                        order = cast.order,
                    )
                },
            crew =
                crew.map { (crew, jobs) ->
                    SeriesCrew(
                        adult = crew.adult,
                        gender = Gender.entries[crew.gender],
                        id = crew.id,
                        knownForDepartment = crew.knownForDepartment,
                        name = crew.name,
                        originalName = crew.originalName,
                        popularity = crew.popularity,
                        profileImage = crew.profileImage?.let { ProfileImage(it) },
                        department = crew.department,
                        totalEpisodeCount = crew.totalEpisodeCount,
                        jobs =
                            jobs.map { job ->
                                Job(
                                    creditId = job.creditId,
                                    job = job.job,
                                    episodeCount = job.episodeCount,
                                )
                            },
                    )
                },
        )
}
