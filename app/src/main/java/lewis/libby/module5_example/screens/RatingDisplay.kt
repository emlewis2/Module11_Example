package lewis.libby.module5_example.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import lewis.libby.module5_example.R
import lewis.libby.module5_example.Screen
import lewis.libby.module5_example.components.MovieScaffold
import lewis.libby.module5_example.components.SimpleText
import lewis.libby.module5_example.repository.RatingWithMoviesDto

@Composable
fun RatingDisplay(
    ratingId: String,
    fetchRatingWithMovies: suspend (String) -> RatingWithMoviesDto,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    onMovieClick: (String) -> Unit,
) {
    var ratingWithMoviesDto by remember { mutableStateOf<RatingWithMoviesDto?>(null) }

    LaunchedEffect(key1 = ratingId) {
        // starts a coroutine to fetch the rating
        ratingWithMoviesDto = fetchRatingWithMovies(ratingId)
    }

    MovieScaffold(
        title = ratingWithMoviesDto?.rating?.name ?: stringResource(id = R.string.loading),
        onSelectListScreen = onSelectListScreen,
        onResetDatabase = onResetDatabase,
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxHeight()
        ) {
            ratingWithMoviesDto?.let { ratingWithMovies ->
                SimpleText(text = ratingWithMovies.rating.description)
                Divider()
                if (ratingWithMovies.movies.isEmpty()) {
                    SimpleText(text = stringResource(id = R.string.no_movies_found_for_this_rating))
                } else {
                    SimpleText(
                        text = stringResource(id = R.string.movies, ratingWithMovies.rating.name)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f) // take up the rest of the screen
                    ) {
                        items(
                            items = ratingWithMovies.movies,
                        ) { movie ->
                            Card(
                                elevation = 4.dp,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                            ) {
                                SimpleText(text = movie.title) {
                                    onMovieClick(movie.id)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}