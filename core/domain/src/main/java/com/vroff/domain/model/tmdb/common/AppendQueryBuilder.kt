package com.vroff.domain.model.tmdb.common

import com.vroff.domain.model.constants.SymbolConstant

fun <T : AppendableResponse> buildAppendQuery(vararg items: T): String =
    items.joinToString(SymbolConstant.COMMA) {
        it.value
    }
