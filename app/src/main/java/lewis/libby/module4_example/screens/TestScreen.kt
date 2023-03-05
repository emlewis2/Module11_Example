package lewis.libby.module4_example.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import lewis.libby.module4_example.ActorList
import lewis.libby.module4_example.MovieList
import lewis.libby.module4_example.MovieViewModel
import lewis.libby.module4_example.RatingList
import lewis.libby.module4_example.RatingScreen
import lewis.libby.module4_example.components.SimpleButton

@Composable
fun TestScreen(
    viewModel: MovieViewModel = viewModel()
) {
    // This is a hideous, but simple simple test screen
    //   (There are some bad things happening here... We'll fix it!)
    // Don't worry about how it does its job; we'll cover that soon!
    Column {
        Row {
            SimpleButton("Reset") {
                viewModel.resetDatabase()
            }
            SimpleButton("Ratings") {
                viewModel.switchTo(RatingList)
            }
        }
        Row {
            SimpleButton("Movies") {
                viewModel.switchTo(MovieList)
            }
            SimpleButton("Actors") {
                viewModel.switchTo(ActorList)
            }
        }

        val ratings by viewModel.ratingsFlow.collectAsState(initial = emptyList())
        val movies by viewModel.moviesFlow.collectAsState(initial = emptyList())
        val actors by viewModel.actorsFlow.collectAsState(initial = emptyList())

        when(val screen = viewModel.screen) {
            RatingList -> RatingList(ratings = ratings) { id ->
                viewModel.switchTo(RatingScreen(id))
            }
            MovieList -> MovieList(movies = movies)
            ActorList -> ActorList(actors = actors)
            is RatingScreen -> RatingDisplay(
                ratingId = screen.id,
                fetchRatingWithMovies = { id ->
                    viewModel.getRatingWithMovies(id)
                }
            )
        }
    }
}