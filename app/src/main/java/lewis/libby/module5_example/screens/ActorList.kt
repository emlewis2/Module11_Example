package lewis.libby.module5_example.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import lewis.libby.module5_example.R
import lewis.libby.module5_example.Screen
import lewis.libby.module5_example.components.MovieScaffold
import lewis.libby.module5_example.components.SimpleText
import lewis.libby.module5_example.repository.ActorDto

@Composable
fun ActorList(
    actors: List<ActorDto>,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    onActorClick: (String) -> Unit,
) = MovieScaffold(
    title = stringResource(id = R.string.screen_title_actors),
    onSelectListScreen = onSelectListScreen,
    onResetDatabase = onResetDatabase,
) { paddingValues ->
    LazyColumn(
        modifier = Modifier.padding(paddingValues)
    ) {
        items(
            items = actors,
            key = { it.id },
        ) {
            Card(
                elevation = 4.dp,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                SimpleText(text = it.name) {
                    onActorClick(it.id)
                }
            }
        }
    }
}