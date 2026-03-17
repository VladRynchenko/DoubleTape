package com.vroff.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun hoursAndMinutesUsingFormat(totalMinutes: Long?): String {
    if (totalMinutes == null) {
        return ""
    }

    val hours = totalMinutes / 60
    val remainingMinutes = totalMinutes % 60
    return stringResource(R.string.hours_duration, hours, remainingMinutes)
}

@Composable
fun seasonsAndSeriesCountFormat(
    seasonsCount: Int?,
    episodesCount: Int?,
): String {
    if (seasonsCount == null || episodesCount == null) {
        throw IllegalArgumentException("Can't be null")
    }
    return stringResource(R.string.seasons_and_episodes, seasonsCount, episodesCount)
}

@Composable
fun formatDate(input: String): String =
    try {
        val parsedDate = LocalDate.parse(input, DateTimeFormatter.ISO_DATE)
        parsedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    } catch (e: Exception) {
        input
    }
