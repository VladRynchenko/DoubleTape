package com.vroff.network.paging

import androidx.paging.PagingData
import androidx.paging.map

fun <DTO : Any, Domain : Any> PagingData<DTO>.mapToDomain(mapper: (DTO) -> Domain): PagingData<Domain> =
    this.map { dto -> mapper(dto) }
