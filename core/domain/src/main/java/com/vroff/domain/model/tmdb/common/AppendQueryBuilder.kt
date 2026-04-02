package com.vroff.domain.model.tmdb.common

import com.vroff.domain.model.constants.SymbolConstant

fun <T : AppendableResponse> buildAppendQuery(items: List<T>): String =
    items.joinToString(SymbolConstant.COMMA) {
        it.value
    }
