package com.vroff.doubletape.detail.movie.credits

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vroff.domain.model.constants.SymbolConstant
import com.vroff.domain.model.tmdb.common.BaseCredits
import com.vroff.domain.model.tmdb.common.Cast
import com.vroff.domain.model.tmdb.movie.Crew
import com.vroff.doubletape.detail.R
import com.vroff.doubletape.detail.profile.CreditType
import com.vroff.ui.ui.LocalInnerPadding
import com.vroff.ui.ui.item.SearchBaseCard
import kotlin.collections.component1
import kotlin.collections.component2

@Composable
fun FullCreditsScreen(
    credits: BaseCredits,
    onPersonItemClick: (Int) -> Unit,
) {
    val fullCreditsCastViewModel = hiltViewModel<FullCreditsCastViewModel>()
    val paddings = LocalInnerPadding.current
    val availableTabs by remember(credits) {
        mutableStateOf(listOf(CreditType.Cast, CreditType.Crew))
    }

    val selectedTab by fullCreditsCastViewModel.selectedTab.collectAsStateWithLifecycle()
    val groupedCrew = credits.crew.groupBy { it.department }

    LazyColumn(
        contentPadding = paddings,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(horizontal = 12.dp),
    ) {
        item { Tabs(availableTabs, selectedTab, fullCreditsCastViewModel::onTabSelected) }

        when (selectedTab) {
            CreditType.Cast -> {
                items(credits.cast) {
                    SearchBaseCard(
                        image = it.profileImage,
                        title = it.name,
                        subtitle =
                            if (it is Cast) {
                                it.character
                            } else {
                                it.roles?.joinToString(SymbolConstant.MIDDLE_POINT) { role ->
                                    role?.character
                                        ?: SymbolConstant.EMPTY
                                }
                            },
                        onClick = { onPersonItemClick(it.id) },
                    )
                }
            }

            CreditType.Crew -> {
                groupedCrew.forEach { (string, crews) ->
                    item { Text(string) }
                    items(crews) {
                        val subtitle =
                            if (it is Crew) {
                                "${it.department} ${SymbolConstant.MIDDLE_POINT} ${it.job}"
                            } else {
                                val jobs = it.jobs?.joinToString(SymbolConstant.COMMA_SPACE) { job -> job.job }
                                "${it.department} ${SymbolConstant.MIDDLE_POINT} $jobs"
                            }
                        SearchBaseCard(
                            image = it.profileImage,
                            title = it.name,
                            subtitle = subtitle,
                            onClick = { onPersonItemClick(it.id) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Tabs(
    availableTabs: List<CreditType>,
    selectedTab: CreditType,
    onTabSelected: (CreditType) -> Unit,
    modifier: Modifier = Modifier,
) {
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
