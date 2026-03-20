package com.vroff.doubletape.detail.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.tmdb.common.BaseDetails
import com.vroff.domain.model.tmdb.common.CastBase
import com.vroff.domain.model.tmdb.common.Genre
import com.vroff.domain.model.tmdb.movie.Credits
import com.vroff.domain.model.tmdb.movie.MovieDetail
import com.vroff.domain.model.tmdb.search.MediaType
import com.vroff.domain.model.tmdb.series.Season
import com.vroff.domain.model.tmdb.series.SeriesDetail
import com.vroff.doubletape.detail.R
import com.vroff.ui.FormatConstant
import com.vroff.ui.ShowFormatter.formatCurrency
import com.vroff.ui.ShowFormatter.formatOriginRun
import com.vroff.ui.ShowFormatter.formatRealiseDate
import com.vroff.ui.ShowFormatter.formatRuntime
import com.vroff.ui.ShowFormatter.formatSeasonsAndSeries
import com.vroff.ui.ShowFormatter.joinWithComma
import com.vroff.ui.SymbolConstant
import com.vroff.ui.ui.ErrorScreen
import com.vroff.ui.ui.ExpandableText
import com.vroff.ui.ui.LoadingScreen
import com.vroff.ui.ui.SearchBaseCard
import com.vroff.ui.ui.detail.CastWidget
import com.vroff.ui.ui.toRequest
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun MovieScreen(
    tmdbId: Int,
    type: MediaType,
    padding: PaddingValues,
) {
    val movieViewModel = hiltViewModel<MovieViewModel>()
    movieViewModel.setTMDBId(tmdbId, type)
    val state = movieViewModel.showState.collectAsStateWithLifecycle()
    ShowDetailsContent(state.value, padding, Modifier.padding(horizontal = 12.dp))
}

@Composable
private fun ShowDetailsContent(
    state: MovieViewModel.ScreenState,
    padding: PaddingValues,
    modifier: Modifier,
) {
    when (state) {
        MovieViewModel.ScreenState.Loading -> {
            LoadingScreen()
        }

        is MovieViewModel.ScreenState.Error -> {
            ErrorScreen(errorText = state.e.toString())
        }

        is MovieViewModel.ScreenState.Success -> {
            DetailsScreen(state.data, padding, modifier)
        }
    }
}

@Composable
fun DetailsScreen(
    show: BaseDetails,
    padding: PaddingValues,
    modifier: Modifier,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        PosterImageWidget(show.posterImage)
        TitleWidget(show.title, modifier.padding(vertical = 12.dp))
        MetadataSection(show, modifier)
        show.credits?.cast?.takeIf { it.isNotEmpty() }?.let {
            CastSection(modifier, it)
        }
        SecondaryMetadataSection(modifier, show)
        show.seasons?.let {
            SeasonsSection(modifier, it)
        }
        OverviewSection(modifier, overview = show.overview)
        Spacer(
            Modifier
                .height(padding.calculateBottomPadding())
                .fillMaxWidth(),
        )
    }
}

@Composable
fun SeasonsSection(
    modifier: Modifier,
    seasons: List<Season>,
) {
    Text(
        stringResource(R.string.header_seasons),
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier,
    )

    seasons.forEach { season ->
        SearchBaseCard(
            modifier,
            season.posterImage,
            season.voteAverage,
            season.name,
            date = formatRealiseDate(season.airDate, FormatConstant.FORMAT_YEAR),
            subtitle =
                formatSeasonsAndSeries(
                    LocalContext.current,
                    episodes = season.episodeCount,
                ),
        ) { }
    }
}

@Composable
fun SecondaryMetadataSection(
    modifier: Modifier = Modifier,
    show: BaseDetails,
) {
    Column(modifier) {
        val credits = show.credits
        val authors: String? =
            when {
                !show.createdBy.isNullOrEmpty() -> {
                    show.createdBy?.joinToString(SymbolConstant.MIDDLE_POINT) { it.name }
                }

                credits is Credits -> {
                    credits.getKeyCreators().joinToString(SymbolConstant.MIDDLE_POINT)
                }

                else -> null
            }

        authors?.let {
            AnnotatedMetadata(
                stringResource(R.string.label_authors),
                it,
            )
        }

        AnnotatedMetadata(
            stringResource(R.string.label_country),
            show.productionCountries.map { it.name }.joinWithComma(),
        )

        show.originalTitle.takeIf { it != show.title }?.let { originalTitle ->
            AnnotatedMetadata(
                label = stringResource(R.string.label_original_title),
                value = originalTitle,
            )
        }

        AnnotatedMetadata(
            stringResource(R.string.label_language),
            show.spokenLanguages.map { it.englishName }.joinWithComma(),
        )

        show.budget?.let {
            AnnotatedMetadata(
                stringResource(R.string.label_budget),
                formatCurrency(it),
            )
        }

        show.revenue?.let {
            AnnotatedMetadata(
                stringResource(R.string.label_revenue),
                formatCurrency(it),
            )
        }
    }
}

@Composable
fun MetadataSection(
    show: BaseDetails,
    modifier: Modifier,
) {
    val context = LocalContext.current
    when (show) {
        is SeriesDetail -> {
            BaseMetadata(
                modifier,
                rating = show.voteAverage,
                genre = show.genres,
                status = show.status,
                runtime =
                    formatSeasonsAndSeries(
                        context,
                        show.numberOfSeasons,
                        show.numberOfEpisodes,
                        show.episodeRunTime
                            .average()
                            .takeIf { !it.isNaN() }
                            ?.roundToInt(),
                    ),
                firstAirDate = formatRealiseDate(show.firstAirDate),
                lastAirDate = show.lastAirDate?.let { formatRealiseDate(it) },
                inProduction = show.inProduction,
            )
        }

        is MovieDetail -> {
            BaseMetadata(
                modifier,
                rating = show.voteAverage,
                genre = show.genres,
                status = show.status,
                runtime = formatRuntime(context, show.runtime),
                releaseDate = formatRealiseDate(show.releaseDate),
            )
        }
    }
}

@Composable
fun BaseMetadata(
    modifier: Modifier = Modifier,
    rating: Float,
    genre: List<Genre> = listOf(),
    status: String,
    runtime: String,
    releaseDate: String? = null,
    firstAirDate: String? = null,
    lastAirDate: String? = null,
    inProduction: Boolean? = null,
) {
    Column(modifier) {
        AnnotatedMetadata(
            stringResource(R.string.label_rating),
            FormatConstant.ROUND_ONE_DIGIT.format(Locale.ROOT, rating),
        )
        AnnotatedMetadata(
            stringResource(R.string.label_genre),
            genre.joinToString(SymbolConstant.MIDDLE_POINT) { it.name },
        )
        releaseDate?.let {
            AnnotatedMetadata(
                stringResource(R.string.label_release_date),
                it,
            )
        }

        inProduction?.let { inProduction ->
            if (inProduction) {
                firstAirDate?.takeIf { it.isNotBlank() }?.let {
                    AnnotatedMetadata(
                        stringResource(R.string.label_first_air_date),
                        formatRealiseDate(it),
                    )
                }
            } else {
                AnnotatedMetadata(
                    stringResource(R.string.label_origin_run),
                    formatOriginRun(firstAirDate, lastAirDate),
                )
            }
        }

        AnnotatedMetadata(stringResource(R.string.label_runtime), runtime)
        AnnotatedMetadata(stringResource(R.string.label_status), status)
    }
}

@Composable
fun AnnotatedMetadata(
    label: String,
    value: String?,
    modifier: Modifier = Modifier,
) {
    val annotatedString =
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("$label: ") }
            append(value ?: SymbolConstant.HYPHEN)
        }
    Text(text = annotatedString, style = MaterialTheme.typography.bodyLarge, modifier = modifier.fillMaxWidth())
}

@Composable
fun CastSection(
    modifier: Modifier = Modifier,
    castList: List<CastBase> = listOf(),
    contentPadding: PaddingValues = PaddingValues(12.dp),
) {
    Column {
        Text(
            stringResource(R.string.label_cast),
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier,
        )
        CastWidget(
            cast = castList,
            contentPadding = contentPadding,
        )
    }
}

@Composable
fun OverviewSection(
    modifier: Modifier = Modifier,
    header: String = stringResource(R.string.header_overview),
    overview: String,
) {
    Text(
        header,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier,
    )
    ExpandableText(
        overview,
        minLines = 4,
        modifier = modifier,
    )
}

@Composable
fun TitleWidget(
    title: String,
    modifier: Modifier,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier,
    )
}

@Composable
fun PosterImageWidget(posterImage: PosterImage?) {
    AsyncImage(
        posterImage?.toRequest() ?: com.vroff.ui.R.drawable.placeholder,
        contentDescription = "Movie poster",
        contentScale = ContentScale.Crop,
        modifier =
            Modifier
                .fillMaxWidth()
                .aspectRatio(0.667f),
    )
}
