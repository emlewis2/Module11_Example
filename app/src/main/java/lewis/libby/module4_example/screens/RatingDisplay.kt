package lewis.libby.module4_example.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import lewis.libby.module4_example.components.SimpleText
import lewis.libby.module4_example.repository.RatingWithMoviesDto

@Composable
fun RatingDisplay(
    ratingId: String,
    fetchRatingWithMovies: suspend (String) -> RatingWithMoviesDto,
) {
    var ratingWithMoviesDto by remember { mutableStateOf<RatingWithMoviesDto?>(null) }

    LaunchedEffect(key1 = ratingId) {
        // starts a coroutine to fetch the rating
        ratingWithMoviesDto = fetchRatingWithMovies(ratingId)
    }

    SimpleText(text = "Rating")
    ratingWithMoviesDto?.let { ratingWithMovies ->
        SimpleText(text = ratingWithMovies.rating.name)
        ratingWithMovies.movies.forEach { movie ->
            SimpleText(text = "Movie: ${movie.title}")
        }
    }
}