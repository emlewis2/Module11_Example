package lewis.libby.module11_example.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import lewis.libby.module11_example.R
import lewis.libby.module11_example.Screen
import lewis.libby.module11_example.components.MovieScaffold
import lewis.libby.module11_example.components.SimpleText
import lewis.libby.module11_example.repository.ActorWithFilmographyDto

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
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxHeight()
        ) {
            actorWithFilmographyDto?.let { actorWithFilmography ->
                SimpleText(
                    text = stringResource(
                        id = R.string.filmography, actorWithFilmography.actor.name
                    )
                )
                LazyColumn(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f) // take up the rest of the screen
                ) {
                    items(
                        items = actorWithFilmography.filmography,
                    ) { roleWithMovie ->
                        Card(
                            elevation = 4.dp,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            SimpleText(
                                text = "${roleWithMovie.movie.title} (${roleWithMovie.character})",
                                modifier = Modifier.clickable {
                                    onMovieClick(roleWithMovie.movie.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}