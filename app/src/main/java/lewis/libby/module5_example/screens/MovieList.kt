package lewis.libby.module5_example.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import lewis.libby.module5_example.R
import lewis.libby.module5_example.Screen
import lewis.libby.module5_example.components.ListScaffold
import lewis.libby.module5_example.components.SimpleText
import lewis.libby.module5_example.repository.MovieDto

@Composable
fun MovieList(
    movies: List<MovieDto>,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    onMovieClick: (String) -> Unit,
    selectedItemIds: Set<String>,
    onClearSelections: () -> Unit,
    onToggleSelection: (String) -> Unit,
    onDeleteSelectedItems: () -> Unit,
) = ListScaffold(
    titleId = R.string.screen_title_movies,
    items = movies,
    getId = { it.id },
    onSelectListScreen = onSelectListScreen,
    onResetDatabase = onResetDatabase,
    onItemClick = onMovieClick,
    itemIcon = Icons.Default.Person,
    itemIconContentDescriptionId = R.string.tap_to_toggle_selection,
    selectedItemIds = selectedItemIds,
    onClearSelections = onClearSelections,
    onToggleSelection = onToggleSelection,
    onDeleteSelectedItems = onDeleteSelectedItems,
) { movie ->
    SimpleText(text = movie.title)
}