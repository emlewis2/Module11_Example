package lewis.libby.module4_example.screens

import androidx.compose.runtime.Composable
import lewis.libby.module4_example.components.SimpleText

@Composable
fun MovieDisplay(
    movieId: String,
) {
    SimpleText(text = "Movie $movieId")
}