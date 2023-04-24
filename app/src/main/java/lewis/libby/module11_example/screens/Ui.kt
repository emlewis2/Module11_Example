package lewis.libby.module11_example.screens

import android.content.Context
import android.graphics.Movie
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import lewis.libby.module11_example.ActorDisplay
import lewis.libby.module11_example.ActorList
import lewis.libby.module11_example.MovieDisplay
import lewis.libby.module11_example.MovieEdit
import lewis.libby.module11_example.MovieList
import lewis.libby.module11_example.MovieViewModel
import lewis.libby.module11_example.RatingDisplay
import lewis.libby.module11_example.RatingList
import lewis.libby.module11_example.repository.createRepository

//class MovieViewModelFactory(private val context: Context): ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        @Suppress("UNCHECKED_CAST")
//        return MovieViewModel(
//            createRepository(context)
//        ) as T
//    }
//}

@Composable
fun Ui(
//    context: Context,
//    viewModel: MovieViewModel =
//        viewModel(factory = MovieViewModelFactory(context)),
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
            },
            onEdit = { id ->
                viewModel.pushScreen(MovieEdit(id))
            }
        )
        is MovieEdit -> MovieEdit(
            movieId = screen.id,
            fetchMovie = viewModel::getMovie,
            onSelectListScreen = viewModel::setScreenStack,
            onResetDatabase = viewModel::resetDatabase,
            onMovieUpdate = viewModel::updateMovie,
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