package lewis.libby.module5_example.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import lewis.libby.module5_example.R
import lewis.libby.module5_example.Screen
import lewis.libby.module5_example.components.ListScaffold
import lewis.libby.module5_example.components.SimpleText
import lewis.libby.module5_example.repository.ActorDto

@Composable
fun ActorList(
    actors: List<ActorDto>,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    selectedItemIds: Set<String>,
    onClearSelections: () -> Unit,
    onToggleSelection: (String) -> Unit,
    onDeleteSelectedItems: () -> Unit,
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
    selectedItemIds = selectedItemIds,
    onClearSelections = onClearSelections,
    onToggleSelection = onToggleSelection,
    onDeleteSelectedItems = onDeleteSelectedItems,
) { actor ->
    SimpleText(text = actor.name)
}