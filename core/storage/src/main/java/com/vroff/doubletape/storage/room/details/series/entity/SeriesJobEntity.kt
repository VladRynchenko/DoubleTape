package com.vroff.doubletape.storage.room.details.series.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.vroff.domain.model.tmdb.series.Job

data class SeriesCrewWithJobs(
    @Embedded val crew: SeriesCrewEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "crewId",
    )
    val jobs: List<SeriesJobEntity>,
)

@Entity(tableName = "jobs", primaryKeys = ["crewId", "creditId"])
data class SeriesJobEntity(
    val crewId: Int,
    val creditId: String,
    val job: String,
    val episodeCount: Int,
) {
    fun toDomain() =
        Job(
            creditId = creditId,
            job = job,
            episodeCount = episodeCount,
        )
}

fun Job.toEntity(crewId: Int): SeriesJobEntity =
    SeriesJobEntity(
        crewId = crewId,
        creditId = creditId,
        job = job,
        episodeCount = episodeCount,
    )
