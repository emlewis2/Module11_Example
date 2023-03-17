package lewis.libby.module5_example.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import lewis.libby.module5_example.ActorList
import lewis.libby.module5_example.MovieList
import lewis.libby.module5_example.R
import lewis.libby.module5_example.RatingList
import lewis.libby.module5_example.Screen

@Composable
fun MovieScaffold(
    title: String,
    selectedItemCount: Int = 0,
    onClearSelections: () -> Unit = {},
    onDeleteSelectedItems: () -> Unit = {},
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    onEdit: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            if (selectedItemCount == 0) {
                TopAppBar(
                    title = { SimpleText(text = title) },
                    actions = {
                        onEdit?.let { onEdit ->
                            IconButton(
                                onClick = onEdit,
                                modifier = Modifier.padding(8.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = stringResource(id = R.string.edit),
                                )
                            }
                        }
                        IconButton(
                            onClick = onResetDatabase,
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = stringResource(id = R.string.reset_database)
                            )
                        }
                    }
                )
            } else {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = onClearSelections,
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.clear_selections),
                            )
                        }
                    },
                    title = { SimpleText(text = selectedItemCount.toString()) },
                    actions = {
                        IconButton(
                            onClick = onDeleteSelectedItems,
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.delete_selected_items)
                            )
                        }
                    }
                )
            }

        },
        content = { paddingValues ->
            content(paddingValues)
        },
        bottomBar = {
            BottomNavigation {
                ScreenSelectButton(
                    targetScreen = RatingList,
                    imageVector = Icons.Default.Emergency,
                    labelId = R.string.screen_title_ratings,
                    onSelectListScreen = onSelectListScreen
                )
                ScreenSelectButton(
                    targetScreen = MovieList,
                    imageVector = Icons.Default.Movie,
                    labelId = R.string.screen_title_movies,
                    onSelectListScreen = onSelectListScreen
                )
                ScreenSelectButton(
                    targetScreen = ActorList,
                    imageVector = Icons.Default.Person,
                    labelId = R.string.screen_title_actors,
                    onSelectListScreen = onSelectListScreen
                )
            }
        }
    )
}

@Composable
fun RowScope.ScreenSelectButton(
    targetScreen: Screen,
    imageVector: ImageVector,
    @StringRes labelId: Int,
    onSelectListScreen: (Screen) -> Unit,
) =
    BottomNavigationItem(
        selected = false,
        icon = {
            Icon(
                imageVector = imageVector,
                contentDescription = stringResource(id = labelId)
            )
        },
        label = {
            Text(text = stringResource(id = labelId))
        },
        onClick = {
            onSelectListScreen(targetScreen)
        }
    )

