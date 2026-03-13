package com.vroff.network

import androidx.paging.PagingData
import androidx.paging.map

fun <DTO : Any, Domain : Any> PagingData<DTO>.mapToDomain(
    mapper: (DTO) -> Domain
): PagingData<Domain> {
    return this.map { dto -> mapper(dto) }
}