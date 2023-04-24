package lewis.libby.module11_example.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import lewis.libby.module11_example.Screen

@Composable
fun <T> ListScaffold(
    @StringRes titleId: Int,
    items: List<T>,
    selectedItemIds: Set<String>,
    onClearSelections: () -> Unit,
    onToggleSelection: (String) -> Unit,
    onDeleteSelectedItems: () -> Unit,
    getId: (T) -> String,
    onSelectListScreen: (Screen) -> Unit,
    onResetDatabase: () -> Unit,
    onItemClick: (String) -> Unit,
    itemIcon: ImageVector,
    @StringRes itemIconContentDescriptionId: Int,
    itemContent: @Composable RowScope. (T) -> Unit,
) = MovieScaffold(
    title = stringResource(id = titleId),
    onSelectListScreen = onSelectListScreen,
    selectedItemCount = selectedItemIds.size,
    onResetDatabase = onResetDatabase,
    onDeleteSelectedItems = onDeleteSelectedItems,
    onClearSelections = onClearSelections,
) { paddingValues ->
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .padding(8.dp)
    ) {
        items(
            items = items,
            key = { getId(it) },
        ) { item ->
            val id = getId(item)

            val backgroundColor =
                if (id in selectedItemIds) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.surface
                }

            val contentColor = MaterialTheme.colors.contentColorFor(backgroundColor)

            // The .pointerInput modifier captures any values referenced - it would never
            //   know the selections have changed. By using rememberUpdatedState, we create
            //   a bucket in the tree that can be updated on recomposition and force
            //   the pointerInput lambda to always get the latest value
            // (Fortunately you don't need to do this often, but if a lambda doesn't seem to
            //   see changes in parameters, you may need to do this.)
            val selectedIds by rememberUpdatedState(newValue = selectedItemIds)
            Card(
                elevation = 4.dp,
                backgroundColor = backgroundColor,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()

                    // NOTE: pointerInput captures the value of selectedItemIds
                    //       By referencing it through a rememberUpdatedState above, it'll see
                    //       updates. Note that we _must_ reference selectedIds inside the lambda
                    //       to see the updated value.
                    //       Note - we could also pass selectedItemIds instead of true - this would
                    //              force pointerInput to reinitialize whenever the selection changes
                    //              That might be expensive to recreate the gesture handler every
                    //              time, OR if the gesture handler held state that would be cleared
                    //              might not give the proper results.
                    .pointerInput(true) {
                        detectTapGestures(
                            onLongPress = {
                                onToggleSelection(id)
                            },
                            onTap = {
                                if (selectedIds.isNotEmpty()) {
                                    onToggleSelection(id)
                                } else {
                                    onItemClick(id)
                                }
                            }
                        )
                    },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = itemIcon,
                        tint = backgroundColor,
                        contentDescription = stringResource(id = itemIconContentDescriptionId),
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = contentColor,
                                shape = CircleShape
                            )
                            .clickable {
                                onToggleSelection(id)
                            }
                    )
                    itemContent(item)
                }
            }
        }
    }
}