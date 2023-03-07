package lewis.libby.module4_example.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import lewis.libby.module4_example.R
import lewis.libby.module4_example.Screen
import lewis.libby.module4_example.components.MovieScaffold
import lewis.libby.module4_example.components.SimpleText
import lewis.libby.module4_example.repository.ActorWithFilmographyDto

@Composable
fun ActorDisplay(
    actorId: String,
    fetchActorWithFilmography: suspend (String) -> ActorWithFilmographyDto,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    onMovieClick: (String) -> Unit,
) {
    var actorWithFilmographyDto by remember { mutableStateOf<ActorWithFilmographyDto?>(null)}

    LaunchedEffect(key1 = actorId) {
        // starts a coroutine to fetch the rating
        actorWithFilmographyDto = fetchActorWithFilmography(actorId)
    }

    MovieScaffold(
        title = actorWithFilmographyDto?.actor?.name ?: stringResource(id = R.string.loading),
        onSelectListScreen = onSelectListScreen,
        onResetDatabase = onResetDatabase,
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            actorWithFilmographyDto?.let { actorWithFilmography ->
                actorWithFilmography.filmography.forEach { roleWithMovie ->
                    SimpleText(text = "${roleWithMovie.movie.title} (${roleWithMovie.character})") {
                        onMovieClick(roleWithMovie.movie.id)
                    }
                }
            }
        }
    }
}