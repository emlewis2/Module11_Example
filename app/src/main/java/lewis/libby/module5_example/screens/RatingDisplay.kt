package lewis.libby.module5_example.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
                .verticalScroll(rememberScrollState())
        ) {
            ratingWithMoviesDto?.let { ratingWithMovies ->
                ratingWithMovies.movies.forEach { movie ->
                    SimpleText(text = movie.title) {
                        onMovieClick(movie.id)
                    }
                }
            }
        }
    }
}