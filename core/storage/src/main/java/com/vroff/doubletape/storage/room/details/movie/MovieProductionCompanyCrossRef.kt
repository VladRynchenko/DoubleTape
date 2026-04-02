package com.vroff.doubletape.storage.room.details.movie

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "movie_company_cross_ref",
    primaryKeys = ["movieId", "companyId"],
    indices = [
        Index(value = ["companyId"]),
    ],
)
class MovieProductionCompanyCrossRef(
    val movieId: Int,
    val companyId: Int,
)
