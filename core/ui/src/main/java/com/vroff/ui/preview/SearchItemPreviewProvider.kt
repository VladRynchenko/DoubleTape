package com.vroff.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.vroff.domain.model.BackdropImage
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.tmdb.common.Genre
import com.vroff.domain.model.tmdb.search.typed.MovieSearchResult
import com.vroff.domain.model.tmdb.search.typed.SeriesSearchResult
import com.vroff.domain.model.tmdb.search.typed.TypedSearchResult
import com.vroff.ui.theme.MovieDDTheme
import com.vroff.ui.ui.SearchItem

class SearchItemPreviewProvider : PreviewParameterProvider<TypedSearchResult> {
    override val values: Sequence<TypedSearchResult>
        get() = sequenceOf(moviePreview, seriesPreview)
}

@Preview
@Composable
fun SearchItemPreview(
    @PreviewParameter(SearchItemPreviewProvider::class) item: TypedSearchResult
) {
    MovieDDTheme() {
        SearchItem(item = item)
    }
}

val moviePreview = MovieSearchResult(
    adult = false,
    backdropImage = BackdropImage(""),
    id = 0,
    title = "Harry Potter and the Philosopher's Stone",
    originalTitle = "Harry Potter and the Philosopher's Stone",
    overview = "Harry Potter has lived under the stairs at his aunt and uncle's house his whole life. But on his 11th birthday, he learns he's a powerful wizard—with a place waiting for him at the Hogwarts School of Witchcraft and Wizardry. As he learns to harness his newfound powers with the help of the school's kindly headmaster, Harry uncovers the truth about his parents' deaths—and about the villain who's to blame.",
    posterImage = PosterImage(""),
    originalLanguage = "en",
    genres = listOf(Genre(14, "Fantasy"), Genre(12, "Adventure")),
    popularity = 23.372,
    releaseDate = "2001-11-16",
    video = false,
    voteAverage = 7.898,
    voteCount = 29255,
)
val seriesPreview = SeriesSearchResult(
    adult = false,
    backdropImage = BackdropImage("/danN2NzJTMouaBEkDWTnyDjDxUt.jpg"),
    id = 103540,
    name = "Percy Jackson and the Olympians",
    originalName = "Percy Jackson and the Olympians",
    overview = "Percy Jackson is on a dangerous quest. Outrunning monsters and outwitting gods, he must journey across America to return Zeus's master bolt and stop an all-out war. With the help of his friends Annabeth and Grover, Percy's journey will lead him closer to the answers he seeks: how to fit into a world where he feels out of place, and who he's destined to be.",
    posterImage = PosterImage("/40eFcTzZier3DWLqldsP5VHxeoD.jpg"),
    originalLanguage = "en",
    genres = listOf(
        Genre(
            10759,
            "Action & Adventure",
        ),
        Genre(
            10765,
            "Sci-Fi & Fantasy",
        ),
        Genre(
            18,
            "Drama",
        ),
        Genre(
            10751,
            "Family",
        ),
    ),
    popularity = 20.6261,
    firstAirDate = "2023-12-19",
    voteAverage = 7.3,
    voteCount = 727,
    originCountry = listOf("US"),
)
