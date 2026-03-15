package com.vroff.domain.util

import java.util.Locale

fun String.capitalizeFirst(): String =
    replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
