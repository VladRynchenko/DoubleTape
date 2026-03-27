package com.vroff.ui

import android.content.Context
import com.vroff.domain.model.constants.FormatConstant
import com.vroff.domain.model.constants.SymbolConstant
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Currency
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object ShowFormatter {
    fun formatRuntime(
        context: Context,
        totalMinutes: Int?,
    ): String? {
        if (totalMinutes == null || totalMinutes <= 0) return null
        val duration = totalMinutes.toDuration(DurationUnit.MINUTES)
        return duration.toComponents { _, hours, minutes, _, _ ->

            val hString = if (hours > 0) context.getString(R.string.hours_duration, hours) else SymbolConstant.EMPTY
            val mRes = if (hours > 0) R.string.minutes_duration_padded else R.string.minutes_duration
            val mString = if (minutes > 0 || hours == 0) context.getString(mRes, minutes) else SymbolConstant.EMPTY
            when {
                hours > 0 && minutes > 0 -> "$hString $mString"
                hours > 0 -> hString
                else -> mString
            }
        }
    }

    fun formatSeasonsAndSeries(
        context: Context,
        seasons: Int? = null,
        episodes: Int? = null,
        avgRuntime: Int? = null,
    ): String {
        val s = seasons?.let { context.resources.getQuantityString(R.plurals.count_seasons, it, it) }
        val e = episodes?.let { context.resources.getQuantityString(R.plurals.count_episodes, it, it) }
        val r = formatRuntime(context, avgRuntime)

        return listOfNotNull(s, e, r.takeIf { it.isNullOrEmpty() })
            .joinToString(" ${SymbolConstant.MIDDLE_POINT} ")
    }

    fun formatRealiseDate(
        input: String?,
        format: String = FormatConstant.FORMAT_DD_MM_YYYY,
    ): String =
        try {
            if (input == null) return SymbolConstant.HYPHEN
            if (input.isEmpty()) return SymbolConstant.TBA
            val parsedDate = LocalDate.parse(input, DateTimeFormatter.ISO_DATE)
            parsedDate.format(DateTimeFormatter.ofPattern(format))
        } catch (e: Exception) {
            SymbolConstant.EMPTY
        }

    fun formatDate(
        input: String?,
        format: String = FormatConstant.FORMAT_DD_MM_YYYY,
    ): String =
        try {
            if (input.isNullOrBlank()) return SymbolConstant.HYPHEN
            val parsedDate = LocalDate.parse(input, DateTimeFormatter.ISO_DATE)
            parsedDate.format(DateTimeFormatter.ofPattern(format))
        } catch (e: Exception) {
            SymbolConstant.EMPTY
        }

    fun formatOriginRun(
        firstAirDate: String?,
        lastAirDate: String?,
        format: String = FormatConstant.FORMAT_YEAR,
    ): String {
        val yearFirst = firstAirDate?.let { formatRealiseDate(firstAirDate, format) }
        val yearLast = lastAirDate?.let { formatRealiseDate(lastAirDate, format) }
        return if (yearFirst == yearLast) {
            yearFirst.orEmpty()
        } else {
            FormatConstant.PERIOD.format(yearFirst, yearLast)
        }
    }

    fun formatCurrency(amount: Long): String {
        if (amount == 0L) return SymbolConstant.HYPHEN
        val formatter = NumberFormat.getCurrencyInstance()
        formatter.currency = Currency.getInstance("USD")
        formatter.maximumFractionDigits = 0

        return formatter.format(amount)
    }

    fun List<String>.joinWithComma(): String = this.joinToString(SymbolConstant.COMMA_SPACE)
}
