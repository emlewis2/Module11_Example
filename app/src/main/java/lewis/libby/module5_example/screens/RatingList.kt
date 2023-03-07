package lewis.libby.module5_example.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import lewis.libby.module5_example.R
import lewis.libby.module5_example.Screen
import lewis.libby.module5_example.components.ListScaffold
import lewis.libby.module5_example.components.SimpleText
import lewis.libby.module5_example.repository.RatingDto

@Composable
fun RatingList(
    ratings: List<RatingDto>,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    onRatingClick: (String) -> Unit,
) = ListScaffold(
    titleId = R.string.screen_title_ratings,
    items = ratings,
    getId = { it.id },
    onSelectListScreen = onSelectListScreen,
    onResetDatabase = onResetDatabase,
    onItemClick = onRatingClick,
    itemIcon = Icons.Default.Person,
    itemIconContentDescriptionId = R.string.tap_to_toggle_selection,
) { rating ->
    SimpleText(text = rating.name)
}