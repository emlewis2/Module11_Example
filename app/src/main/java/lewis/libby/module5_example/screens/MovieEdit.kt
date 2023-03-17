package lewis.libby.module5_example.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
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
import lewis.libby.module5_example.components.TextEntry
import lewis.libby.module5_example.repository.MovieDto

@Composable
fun MovieEdit(
    movieId: String,
    fetchMovie: suspend (String) -> MovieDto,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    onMovieUpdate: (MovieDto) -> Unit,
) {
    var movie by remember { mutableStateOf<MovieDto?>(null) }

    LaunchedEffect(key1 = movieId) {
        // starts a coroutine to fetch the rating
        movie = fetchMovie(movieId)
    }

    MovieScaffold(
        title = movie?.title ?: stringResource(id = R.string.loading),
        onSelectListScreen = onSelectListScreen,
        onResetDatabase = onResetDatabase,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxHeight()
        ) {
            TextEntry(
                labelId = R.string.title,
                placeholderId = R.string.movie_title_placeholder,
                value = movie?.title ?: "",
                onValueChange = {
                    movie = movie?.copy(title = it)?.apply {
                        onMovieUpdate(this)
                    }
                },
            )
            TextEntry(
                labelId = R.string.description,
                placeholderId = R.string.movie_description_placeholder,
                value = movie?.description ?: "",
                onValueChange = {
                    movie = movie?.copy(description = it)?.apply {
                        onMovieUpdate(this)
                    }
                },
            )
        }
    }
}