package com.vroff.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun hoursAndMinutesUsingFormat(totalMinutes: Int?): String {
    if (totalMinutes == null) {
        return ""
    }

    val hours = totalMinutes / 60
    val remainingMinutes = totalMinutes % 60
    return stringResource(R.string.hours_duration, hours, remainingMinutes)
}

@Composable
fun seasonsAndSeriesCountFormat(seasonsCount: Int?, episodesCount: Int?): String {
    if (seasonsCount == null || episodesCount == null) {
        throw IllegalArgumentException("Can't be null")
    }
    return stringResource(R.string.seasons_and_episodes, seasonsCount, episodesCount)
}