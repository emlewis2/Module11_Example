package lewis.libby.module4_example.screens

import androidx.compose.runtime.Composable
import lewis.libby.module4_example.components.SimpleText
import lewis.libby.module4_example.repository.MovieDto

@Composable
fun MovieList(
    movies: List<MovieDto>,
    onMovieClick: (String) -> Unit,
) {
    SimpleText(text = "Movies")
    movies.forEach {
        SimpleText(text = it.title) {
            onMovieClick(it.id)
        }
    }
}