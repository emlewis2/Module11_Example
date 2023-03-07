package lewis.libby.module5_example.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import lewis.libby.module5_example.R
import lewis.libby.module5_example.Screen
import lewis.libby.module5_example.components.ListScaffold
import lewis.libby.module5_example.components.MovieScaffold
import lewis.libby.module5_example.components.SimpleText
import lewis.libby.module5_example.repository.ActorDto

@Composable
fun ActorList(
    actors: List<ActorDto>,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    onActorClick: (String) -> Unit,
) = ListScaffold(
    titleId = R.string.screen_title_actors,
    items = actors,
    getId = { it.id },
    onSelectListScreen = onSelectListScreen,
    onResetDatabase = onResetDatabase,
    onItemClick = onActorClick,
    itemIcon = Icons.Default.Person,
    itemIconContentDescriptionId = R.string.tap_to_toggle_selection,
) { actor ->
    SimpleText(text = actor.name)
}