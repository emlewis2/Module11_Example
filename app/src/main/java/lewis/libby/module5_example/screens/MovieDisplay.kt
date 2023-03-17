package lewis.libby.module5_example.screens

import androidx.compose.foundation.clickable
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
import lewis.libby.module5_example.repository.MovieWithCastDto

@Composable
fun MovieDisplay(
    movieId: String,
    fetchMovieWithCast: suspend (String) -> MovieWithCastDto,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    onActorClick: (String) -> Unit,
    onEdit: (String) -> Unit,
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
        onEdit =
        movieWithCastDto?.let { movieWithCast ->
            {
                onEdit(movieWithCast.movie.id)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxHeight()
        ) {
            movieWithCastDto?.let { movieWithCast ->
                SimpleText(
                    text = movieWithCast.movie.description
                )
                Divider()
                SimpleText(
                    text = stringResource(id = R.string.cast)
                )
                LazyColumn(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f) // take up the rest of the screen
                ) {
                    items(
                        items = movieWithCast.cast,
                    ) { roleWithActor ->
                        Card(
                            elevation = 4.dp,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            SimpleText(
                                text = "${roleWithActor.character}: ${roleWithActor.actor.name}",
                                modifier = Modifier.clickable {
                                    onActorClick(roleWithActor.actor.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}