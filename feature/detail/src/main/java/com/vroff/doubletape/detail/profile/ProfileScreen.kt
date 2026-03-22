package com.vroff.doubletape.detail.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
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
import com.vroff.ui.ui.ErrorScreen
import com.vroff.ui.ui.LoadingScreen
import com.vroff.ui.ui.SearchBaseCard
import com.vroff.ui.ui.section.ExternalIdSection
import com.vroff.ui.ui.section.OverviewSection
import com.vroff.ui.ui.toRequest

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileId: Int,
    padding: PaddingValues = PaddingValues(12.dp),
    onShowClick: (Int, MediaType) -> Unit = { _, _ -> },
) {
    val profileViewModel = hiltViewModel<ProfileViewModel>()
    profileViewModel.setProfileId(profileId)
    val state = profileViewModel.state.collectAsStateWithLifecycle()

    ProfileContent(state.value, padding, modifier.padding(horizontal = 12.dp), onShowClick)
}

@Composable
private fun ProfileContent(
    state: BaseScreenState<ProfileDetail>,
    padding: PaddingValues,
    modifier: Modifier,
    onShowClick: (Int, MediaType) -> Unit,
) {
    when (state) {
        is BaseScreenState.Loading -> {
            LoadingScreen()
        }

        is BaseScreenState.Error -> {
            ErrorScreen(errorText = state.e.toString())
        }

        is BaseScreenState.Success -> {
            ProfileDetailsScreen(state.data, padding, modifier, onShowClick)
        }
    }
}

@Composable
fun ProfileDetailsScreen(
    profileDetail: ProfileDetail,
    padding: PaddingValues,
    modifier: Modifier,
    onShowClick: (Int, MediaType) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = padding,
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            HeadSection(profileDetail, Modifier)
        }

        item {
            OverviewSection(Modifier, stringResource(R.string.header_biography), profileDetail.biography)
        }


        profileDetail.combinedCredits?.let { credits ->
            item {
                Text(
                    stringResource(R.string.header_filmography),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier,
                )
            }
            credits.cast?.let { cast ->
                item {
                    Text(
                        stringResource(R.string.header_acting),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 12.dp),
                    )
                }

                val sortedCast =
                    cast.sortedWith(
                        compareByDescending<ShowCredit, String?>(nullsLast()) { item ->
                            item.releaseDate.takeIf { it?.isNotBlank() == true }?.take(4)
                        }.thenByDescending { it.voteAverage },
                    )

                items(
                    sortedCast,
                    key = { "cast_${it.id}_${it.character}" },
                ) { item ->
                    SearchBaseCard(
                        Modifier,
                        item.posterPath,
                        title = item.title.orEmpty(),
                        subtitle = item.character.orEmpty(),
                        rating = if (item.releaseDate?.isEmpty() != true) item.voteAverage else null,
                        date = ShowFormatter.formatRealiseDate(item.releaseDate, FormatConstant.FORMAT_YEAR),
                        onClick = { onShowClick(item.id, item.mediaType) },
                    )
                }
            }
        }
    }
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
        ProfileImageWidget(
            profile.profileImage,
        )
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
    AsyncImage(
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
