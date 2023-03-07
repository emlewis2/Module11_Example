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
import lewis.libby.module5_example.repository.MovieWithCastDto

@Composable
fun MovieDisplay(
    movieId: String,
    fetchMovieWithCast: suspend (String) -> MovieWithCastDto,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    onActorClick: (String) -> Unit,
) {
    var movieWithCastDto by remember { mutableStateOf<MovieWithCastDto?>(null) }

    LaunchedEffect(key1 = movieId) {
        // starts a coroutine to fetch the rating
        movieWithCastDto = fetchMovieWithCast(movieId)
    }

    MovieScaffold(
        title = movieWithCastDto?.movie?.title ?: stringResource(id = R.string.loading),
        onSelectListScreen = onSelectListScreen,
        onResetDatabase = onResetDatabase,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            movieWithCastDto?.let { movieWithCast ->
                movieWithCast.cast.forEach { roleWithActor ->
                    SimpleText(text = "${roleWithActor.character}: ${roleWithActor.actor.name}") {
                        onActorClick(roleWithActor.actor.id)
                    }
                }
            }
        }
    }
}