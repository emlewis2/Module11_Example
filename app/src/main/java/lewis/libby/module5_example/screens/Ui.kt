package lewis.libby.module5_example.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import lewis.libby.module5_example.ActorDisplay
import lewis.libby.module5_example.ActorList
import lewis.libby.module5_example.MovieDisplay
import lewis.libby.module5_example.MovieList
import lewis.libby.module5_example.MovieViewModel
import lewis.libby.module5_example.RatingDisplay
import lewis.libby.module5_example.RatingList

@Composable
fun Ui(
    viewModel: MovieViewModel = viewModel(),
    onExit: () -> Unit,
) {
    BackHandler {
        viewModel.popScreen()
    }

    val ratings by viewModel.ratingsFlow.collectAsState(initial = emptyList())
    val movies by viewModel.moviesFlow.collectAsState(initial = emptyList())
    val actors by viewModel.actorsFlow.collectAsState(initial = emptyList())

    when(val screen = viewModel.screen) {
        null -> onExit()

        RatingList -> RatingList(
            ratings = ratings,
            onSelectListScreen = viewModel::setScreenStack,
            onResetDatabase = viewModel::resetDatabase,
            onRatingClick = { id ->
                viewModel.pushScreen(RatingDisplay(id))
            },
            selectedItemIds = viewModel.selectedItemIds,
            onClearSelections = viewModel::clearSelections,
            onToggleSelection = viewModel::toggleSelection,
            onDeleteSelectedItems = viewModel::deleteSelectedRatings,
        )
        MovieList -> MovieList(
            movies = movies,
            onSelectListScreen = viewModel::setScreenStack,
            onResetDatabase = viewModel::resetDatabase,
            onMovieClick = { id ->
                viewModel.pushScreen(MovieDisplay(id))
            },
            selectedItemIds = viewModel.selectedItemIds,
            onClearSelections = viewModel::clearSelections,
            onToggleSelection = viewModel::toggleSelection,
            onDeleteSelectedItems = viewModel::deleteSelectedMovies,
        )
        ActorList -> ActorList(
            actors = actors,
            onSelectListScreen = viewModel::setScreenStack,
            onResetDatabase = viewModel::resetDatabase,
            onActorClick = { id ->
                viewModel.pushScreen(ActorDisplay(id))
            },
            selectedItemIds = viewModel.selectedItemIds,
            onClearSelections = viewModel::clearSelections,
            onToggleSelection = viewModel::toggleSelection,
            onDeleteSelectedItems = viewModel::deleteSelectedActors,
        )
        is RatingDisplay -> RatingDisplay(
            ratingId = screen.id,
            fetchRatingWithMovies = { id ->
                viewModel.getRatingWithMovies(id)
            },
            onSelectListScreen = viewModel::setScreenStack,
            onResetDatabase = viewModel::resetDatabase,
            onMovieClick = { id ->
                viewModel.pushScreen(MovieDisplay(id))
            }
        )
        is MovieDisplay -> MovieDisplay(
            movieId = screen.id,
            fetchMovieWithCast = viewModel::getMovieWithCast,
            onSelectListScreen = viewModel::setScreenStack,
            onResetDatabase = viewModel::resetDatabase,
            onActorClick = { id ->
                viewModel.pushScreen(ActorDisplay(id))
            }
        )
        is ActorDisplay -> ActorDisplay(
            actorId = screen.id,
            fetchActorWithFilmography = viewModel::getActorWithFilmography,
            onSelectListScreen = viewModel::setScreenStack,
            onResetDatabase = viewModel::resetDatabase,
            onMovieClick = { id ->
                viewModel.pushScreen(MovieDisplay(id))
            }
        )
    }
}