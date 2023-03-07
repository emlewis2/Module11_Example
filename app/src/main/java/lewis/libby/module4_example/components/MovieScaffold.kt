package lewis.libby.module4_example.components

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
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import lewis.libby.module4_example.ActorList
import lewis.libby.module4_example.MovieList
import lewis.libby.module4_example.R
import lewis.libby.module4_example.RatingList
import lewis.libby.module4_example.Screen

@Composable
fun MovieScaffold(
    title: String,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { SimpleText(text = title) },
                actions = {
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

