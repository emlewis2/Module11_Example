package lewis.libby.module4_example.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import lewis.libby.module4_example.ActorDisplay
import lewis.libby.module4_example.ActorList
import lewis.libby.module4_example.MovieDisplay
import lewis.libby.module4_example.MovieList
import lewis.libby.module4_example.MovieViewModel
import lewis.libby.module4_example.RatingDisplay
import lewis.libby.module4_example.RatingList
import lewis.libby.module4_example.components.SimpleButton

@Composable
fun TestScreen(
    viewModel: MovieViewModel = viewModel(),
    onExit: () -> Unit,
) {
    BackHandler {
        viewModel.popScreen()
    }
    // This is a hideous, but simple simple test screen
    //   (There are some bad things happening here... We'll fix it!)
    // Don't worry about how it does its job; we'll cover that soon!
    Column {
        Row {
            SimpleButton("Reset") {
                viewModel.resetDatabase()
            }
            SimpleButton("Ratings") {
                viewModel.setScreenStack(RatingList)
            }
        }
        Row {
            SimpleButton("Movies") {
                viewModel.setScreenStack(MovieList)
            }
            SimpleButton("Actors") {
                viewModel.setScreenStack(ActorList)
            }
        }

        val ratings by viewModel.ratingsFlow.collectAsState(initial = emptyList())
        val movies by viewModel.moviesFlow.collectAsState(initial = emptyList())
        val actors by viewModel.actorsFlow.collectAsState(initial = emptyList())

        when(val screen = viewModel.screen) {
            null -> onExit()

            RatingList -> RatingList(ratings = ratings) { id ->
                viewModel.pushScreen(RatingDisplay(id))
            }
            MovieList -> MovieList(movies = movies) { id ->
                viewModel.pushScreen(MovieDisplay(id))
            }
            ActorList -> ActorList(actors = actors) { id ->
                viewModel.pushScreen(ActorDisplay(id))
            }
            is RatingDisplay -> RatingDisplay(
                ratingId = screen.id,
                fetchRatingWithMovies = { id ->
                    viewModel.getRatingWithMovies(id)
                },
                onMovieClick = { id ->
                    viewModel.pushScreen(MovieDisplay(id))
                }
            )
            is MovieDisplay -> MovieDisplay(
                movieId = screen.id,
            )
            is ActorDisplay -> ActorDisplay(
                actorId = screen.id,
            )
        }
    }
}