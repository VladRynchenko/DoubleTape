package com.vroff.doubletape.detail.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.constants.FormatConstant
import com.vroff.domain.model.constants.SymbolConstant
import com.vroff.domain.model.tmdb.profile.ProfileDetail
import com.vroff.domain.model.tmdb.profile.ShowCredit
import com.vroff.domain.model.tmdb.search.MediaType
import com.vroff.doubletape.detail.R
import com.vroff.ui.ShowFormatter
import com.vroff.ui.model.BaseScreenState
import com.vroff.ui.ui.AnnotatedMetadata
import com.vroff.ui.ui.AppAsyncImage
import com.vroff.ui.ui.ErrorScreen
import com.vroff.ui.ui.LoadingScreen
import com.vroff.ui.ui.LocalInnerPadding
import com.vroff.ui.ui.item.SearchBaseCard
import com.vroff.ui.ui.section.ExternalIdSection
import com.vroff.ui.ui.section.OverviewSection
import com.vroff.ui.ui.toRequest

@Composable
fun ProfileScreen(
    profileId: Int,
    onShowClick: (Int, MediaType) -> Unit = { _, _ -> },
) {
    val profileViewModel = hiltViewModel<ProfileViewModel>()
    profileViewModel.setProfileId(profileId)
    val state by profileViewModel.state.collectAsStateWithLifecycle()
    val selectedTab by profileViewModel.selectedTab.collectAsStateWithLifecycle()
    val isExpanded by profileViewModel.isExpanded.collectAsStateWithLifecycle()
    val availableTabs by profileViewModel.availableTabs.collectAsStateWithLifecycle()

    ProfileContent(
        state = state,
        selectedTab = selectedTab,
        isExpanded = isExpanded,
        availableTabs = availableTabs,
        onTabSelected = profileViewModel::onTabSelected,
        onExpandToggle = profileViewModel::toggleExpanded,
        onShowClick = onShowClick,
        modifier = Modifier.padding(horizontal = 12.dp),
    )
}

@Composable
private fun ProfileContent(
    state: BaseScreenState<ProfileDetail>,
    selectedTab: CreditType,
    isExpanded: Boolean,
    availableTabs: List<CreditType>,
    onTabSelected: (CreditType) -> Unit,
    onExpandToggle: () -> Unit,
    onShowClick: (Int, MediaType) -> Unit,
    modifier: Modifier,
) {
    when (state) {
        is BaseScreenState.Loading -> LoadingScreen()
        is BaseScreenState.Error -> ErrorScreen(errorText = state.e.toString())
        is BaseScreenState.Success ->
            ProfileDetailsScreen(
                profileDetail = state.data,
                selectedTab = selectedTab,
                isExpanded = isExpanded,
                availableTabs = availableTabs,
                onTabSelected = onTabSelected,
                onExpandToggle = onExpandToggle,
                modifier = modifier,
                onShowClick = onShowClick,
            )
    }
}

enum class CreditType { Cast, Crew }

@Composable
fun ProfileDetailsScreen(
    profileDetail: ProfileDetail,
    selectedTab: CreditType,
    isExpanded: Boolean,
    availableTabs: List<CreditType>,
    onTabSelected: (CreditType) -> Unit,
    onExpandToggle: () -> Unit,
    modifier: Modifier,
    onShowClick: (Int, MediaType) -> Unit,
) {
    val padding = LocalInnerPadding.current
    val credits = profileDetail.combinedCredits

    val (displayedCredits, totalCount) =
        remember(credits, selectedTab, isExpanded) {
            val rawItems =
                if (selectedTab == CreditType.Cast) {
                    credits?.cast ?: emptyList()
                } else {
                    credits?.crew ?: emptyList()
                }

            val sorted =
                rawItems.sortedWith(
                    compareByDescending<ShowCredit, String?>(nullsLast()) {
                        it.releaseDate?.takeIf { d -> d.isNotBlank() }?.take(4)
                    }.thenByDescending { it.voteAverage },
                )

            val grouped =
                sorted
                    .groupBy { it.id to it.mediaType }
                    .map { (_, roles) ->
                        val first = roles.first()
                        val mergedSubtitle =
                            if (selectedTab == CreditType.Cast) {
                                roles.mapNotNull { it.character }.distinct().joinToString(", ")
                            } else {
                                roles.map { "${it.department} • ${it.job}" }.distinct().joinToString("\n")
                            }
                        first to mergedSubtitle
                    }

            grouped
                .take(if (isExpanded) Int.MAX_VALUE else 5)
                .groupBy { (item, _) ->
                    ShowFormatter.formatRealiseDate(item.releaseDate, FormatConstant.FORMAT_YEAR)
                } to grouped.size
        }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = padding,
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item { HeadSection(profileDetail, Modifier) }

        item {
            OverviewSection(
                header = stringResource(R.string.header_biography),
                overview = profileDetail.biography,
            )
        }

        if (availableTabs.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.header_filmography),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }

            item {
                FilmographyTabs(
                    availableTabs = availableTabs,
                    selectedTab = selectedTab,
                    onTabSelected = onTabSelected,
                )
            }

            displayedCredits.forEach { (year, groupPairs) ->
                item(key = "header_${selectedTab.name}_$year") {
                    YearHeader(year)
                }

                items(
                    items = groupPairs,
                    key = { (item, _) -> "${selectedTab.name}_${item.id}_${item.mediaType}" },
                ) { (item, mergedSubtitle) ->
                    SearchBaseCard(
                        image = item.posterPath,
                        title = item.title.orEmpty(),
                        subtitle = mergedSubtitle,
                        rating = if (!item.releaseDate.isNullOrEmpty()) item.voteAverage else null,
                        date = ShowFormatter.formatRealiseDate(item.releaseDate, FormatConstant.FORMAT_YEAR),
                        onClick = { onShowClick(item.id, item.mediaType) },
                    )
                }
            }

            if (totalCount > 5) {
                item {
                    ShowMoreButton(
                        totalCount = totalCount,
                        isCurrentlyExpanded = isExpanded,
                        onClick = onExpandToggle,
                    )
                }
            }
        }
    }
}

@Composable
fun FilmographyTabs(
    availableTabs: List<CreditType>,
    selectedTab: CreditType,
    onTabSelected: (CreditType) -> Unit,
) {
    if (availableTabs.size > 1) {
        SecondaryTabRow(
            modifier = Modifier.clip(CircleShape),
            selectedTabIndex = availableTabs.indexOf(selectedTab),
            containerColor = MaterialTheme.colorScheme.surface,
            indicator = {},
            divider = {},
        ) {
            availableTabs.forEach { tab ->
                val isSelected = selectedTab == tab
                Tab(
                    selected = isSelected,
                    onClick = { onTabSelected(tab) },
                    modifier =
                        Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent,
                            ),
                ) {
                    Text(
                        text =
                            if (tab == CreditType.Cast) {
                                stringResource(R.string.header_acting)
                            } else {
                                stringResource(R.string.header_crew)
                            },
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
fun YearHeader(year: String) {
    Text(
        text = year,
        style = MaterialTheme.typography.titleSmall,
        textAlign = TextAlign.End,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp),
    )
}

@Composable
fun ShowMoreButton(
    totalCount: Int,
    isCurrentlyExpanded: Boolean,
    onClick: () -> Unit,
) {
    Text(
        text = if (isCurrentlyExpanded) "Hide" else "Show all ($totalCount)",
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(vertical = 12.dp),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.labelLarge,
    )
}

@Composable
fun HeadSection(
    profile: ProfileDetail,
    modifier: Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        ProfileImageWidget(profile.profileImage)
        Column {
            Text(
                profile.name,
                style = MaterialTheme.typography.headlineMedium,
            )
            AnnotatedMetadata(
                stringResource(R.string.label_birthplace),
                profile.placeOfBirth ?: SymbolConstant.HYPHEN,
            )
            AnnotatedMetadata(
                stringResource(R.string.label_birthday),
                ShowFormatter.formatDate(profile.birthday, FormatConstant.FORMAT_DD_MMMM_YYYY),
            )
            AnnotatedMetadata(
                stringResource(R.string.label_gender),
                profile.gender.name,
            )
            profile.externalIds?.let {
                ExternalIdSection(externalIds = it)
            }
        }
    }
}

@Composable
private fun ProfileImageWidget(profileImage: ProfileImage?) {
    AppAsyncImage(
        model = profileImage.toRequest(),
        contentDescription = "Profile image",
        contentScale = ContentScale.Crop,
        modifier =
            Modifier
                .width(98.dp)
                .clip(RoundedCornerShape(14.dp))
                .aspectRatio(0.667f),
    )
}
