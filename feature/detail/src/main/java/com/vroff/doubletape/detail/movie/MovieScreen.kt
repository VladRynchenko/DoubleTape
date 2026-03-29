package com.vroff.doubletape.detail.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.constants.FormatConstant
import com.vroff.domain.model.constants.SymbolConstant
import com.vroff.domain.model.tmdb.common.BaseDetails
import com.vroff.domain.model.tmdb.common.CastBase
import com.vroff.domain.model.tmdb.common.Genre
import com.vroff.domain.model.tmdb.movie.Credits
import com.vroff.domain.model.tmdb.movie.MovieDetail
import com.vroff.domain.model.tmdb.search.MediaType
import com.vroff.domain.model.tmdb.series.Season
import com.vroff.domain.model.tmdb.series.SeriesDetail
import com.vroff.doubletape.detail.R
import com.vroff.ui.ShowFormatter.formatCurrency
import com.vroff.ui.ShowFormatter.formatOriginRun
import com.vroff.ui.ShowFormatter.formatRealiseDate
import com.vroff.ui.ShowFormatter.formatRuntime
import com.vroff.ui.ShowFormatter.formatSeasonsAndSeries
import com.vroff.ui.ShowFormatter.joinWithComma
import com.vroff.ui.model.BaseScreenState
import com.vroff.ui.ui.AnnotatedMetadata
import com.vroff.ui.ui.AppAsyncImage
import com.vroff.ui.ui.ErrorScreen
import com.vroff.ui.ui.HeaderText
import com.vroff.ui.ui.LoadingScreen
import com.vroff.ui.ui.LocalInnerPadding
import com.vroff.ui.ui.detail.CastWidget
import com.vroff.ui.ui.item.SearchBaseCard
import com.vroff.ui.ui.section.OverviewSection
import com.vroff.ui.ui.toRequest
import com.vroff.ui.ui.widget.Icon
import com.vroff.ui.ui.widget.WideButton
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun MovieScreen(
    tmdbId: Int,
    type: MediaType,
    onCastItemClick: (Int) -> Unit,
    onVideoClick: () -> Unit,
    onFullCreditsClick: () -> Unit,
) {
    val movieViewModel = hiltViewModel<MovieViewModel>()
    movieViewModel.setTMDBId(tmdbId, type)
    val state by movieViewModel.showState.collectAsStateWithLifecycle()
    ShowDetailsContent(
        state = state,
        onCastItemClick = onCastItemClick,
        onVideoClick = onVideoClick,
        onFullCreditsClick = onFullCreditsClick,
        modifier = Modifier.padding(horizontal = 12.dp),
    )
}

@Composable
private fun ShowDetailsContent(
    state: BaseScreenState<BaseDetails>,
    onCastItemClick: (Int) -> Unit,
    onVideoClick: () -> Unit,
    modifier: Modifier,
    onFullCreditsClick: () -> Unit,
) {
    when (state) {
        is BaseScreenState.Loading -> {
            LoadingScreen()
        }

        is BaseScreenState.Error -> {
            ErrorScreen(errorText = state.e.toString())
        }

        is BaseScreenState.Success -> {
            DetailsScreen(state.data, onCastItemClick, onVideoClick, onFullCreditsClick, modifier)
        }
    }
}

@Composable
fun DetailsScreen(
    show: BaseDetails,
    onCastItemClick: (Int) -> Unit,
    onVideoClick: () -> Unit,
    onFullCreditsClick: () -> Unit,
    modifier: Modifier,
) {
    val scrollState = rememberScrollState()
    val padding = LocalInnerPadding.current
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        PosterImageWidget(show.posterImage)
        TitleWidget(show.title, modifier.padding(vertical = 8.dp))
        MetadataSection(modifier, show)
        ActionSection(modifier = modifier.height(60.dp), show, onVideoClick = onVideoClick)
        show.credits?.cast?.takeIf { it.isNotEmpty() }?.take(20)?.let {
            CastSection(
                modifier,
                it,
                onCastItemClick = onCastItemClick,
                onFullCreditsClick = onFullCreditsClick,
            )
        }
        SecondaryMetadataSection(modifier, show)
        show.seasons?.let {
            SeasonsSection(modifier, it)
        }
        OverviewSection(modifier, header = stringResource(R.string.header_overview), overview = show.overview)
        Spacer(
            Modifier
                .height(padding.calculateBottomPadding())
                .fillMaxWidth(),
        )
    }
}

@Composable
fun ActionSection(
    modifier: Modifier = Modifier,
    show: BaseDetails,
    onVideoClick: () -> Unit,
) {
    if (show.videos?.isNotEmpty() == true) {
        Row(modifier) {
            WideButton(
                modifier =
                    Modifier
                        .fillMaxHeight()
                        .weight(1f),
                icon = Icon.Play,
                onClick = onVideoClick,
            )
        }
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
    modifier: Modifier,
    show: BaseDetails,
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
                firstAirDate = show.firstAirDate,
                lastAirDate = show.lastAirDate,
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
    runtime: String?,
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
                firstAirDate?.takeIf { it.isNotEmpty() }?.let {
                    AnnotatedMetadata(
                        stringResource(R.string.label_first_air_date),
                        formatRealiseDate(it, FormatConstant.FORMAT_YEAR),
                    )
                }
            } else {
                AnnotatedMetadata(
                    stringResource(R.string.label_origin_run),
                    formatOriginRun(firstAirDate, lastAirDate),
                )
            }
        }

        runtime?.let {
            AnnotatedMetadata(stringResource(R.string.label_runtime), it)
        }
        AnnotatedMetadata(stringResource(R.string.label_status), status)
    }
}

@Composable
fun CastSection(
    modifier: Modifier = Modifier,
    castList: List<CastBase> = listOf(),
    contentPadding: PaddingValues = PaddingValues(12.dp),
    onCastItemClick: (Int) -> Unit,
    onFullCreditsClick: () -> Unit,
) {
    Column {
        HeaderText(
            modifier = modifier,
            headerText = stringResource(R.string.header_cast),
            isSeeMore = true,
            onMoreClicked = onFullCreditsClick,
        )
        CastWidget(
            cast = castList,
            contentPadding = contentPadding,
            onItemClick = onCastItemClick,
        )
    }
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
    AppAsyncImage(
        posterImage?.toRequest(),
        contentDescription = "Movie poster",
        contentScale = ContentScale.Crop,
        modifier =
            Modifier
                .fillMaxWidth()
                .aspectRatio(0.667f),
    )
}
