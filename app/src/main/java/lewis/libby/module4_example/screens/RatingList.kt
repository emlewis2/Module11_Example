package lewis.libby.module4_example.screens

import androidx.compose.runtime.Composable
import lewis.libby.module4_example.components.SimpleText
import lewis.libby.module4_example.repository.RatingDto

@Composable
fun RatingList(
    ratings: List<RatingDto>,
    onRatingClick: (String) -> Unit,
) {
    SimpleText(text = "Ratings")
    ratings.forEach {
        SimpleText(text = it.name) {
            onRatingClick(it.id)
        }
    }
}