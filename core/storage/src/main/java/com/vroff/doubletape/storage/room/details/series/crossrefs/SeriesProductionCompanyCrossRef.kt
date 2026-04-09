package com.vroff.doubletape.storage.room.details.series.crossrefs

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "series_company_cross_ref",
    primaryKeys = ["seriesId", "companyId"],
    indices = [
        Index(value = ["companyId"]),
    ],
)
class SeriesProductionCompanyCrossRef(
    val seriesId: Int,
    val companyId: Int,
)
