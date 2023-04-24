package lewis.libby.module11_example

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import lewis.libby.module11_example.repository.MovieDatabaseRepository
import lewis.libby.module11_example.repository.MovieDto
import lewis.libby.module11_example.repository.MovieRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed interface Screen
object RatingList: Screen
object MovieList: Screen
object ActorList: Screen

data class RatingDisplay(
    val id: String
): Screen
data class ActorDisplay(
    val id: String
): Screen
data class MovieDisplay(
    val id: String
): Screen
data class MovieEdit(
    val id: String
): Screen

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private var screenStack: List<Screen> = listOf(MovieList)
        set(value) {
            field = value
            screen = value.lastOrNull()
            clearSelections()
        }

    var selectedItemIds by mutableStateOf<Set<String>>(emptySet())
        private set

    fun clearSelections() {
        selectedItemIds = emptySet()
    }
    fun toggleSelection(id: String) {
        selectedItemIds =
            if (id in selectedItemIds) {
                selectedItemIds - id
            } else {
                selectedItemIds + id
            }
    }
    fun deleteSelectedActors() {
        viewModelScope.launch {
            repository.deleteActorsById(selectedItemIds)
            clearSelections()
        }
    }
    fun deleteSelectedMovies() {
        viewModelScope.launch {
            repository.deleteMoviesById(selectedItemIds)
            clearSelections()
        }
    }
    fun deleteSelectedRatings() {
        viewModelScope.launch {
            repository.deleteRatingsById(selectedItemIds)
            clearSelections()
        }
    }

    fun pushScreen(screen: Screen) {
        screenStack = screenStack + screen
    }

    fun popScreen() {
        if (screenStack.isNotEmpty()) {
            screenStack = screenStack.dropLast(1)
        }
    }

    fun setScreenStack(screen: Screen) {
        screenStack = listOf(screen)
    }

    var screen by mutableStateOf<Screen?>(MovieList)
        private set

    val ratingsFlow = repository.ratingsFlow
    val moviesFlow = repository.moviesFlow
    val actorsFlow = repository.actorsFlow

    suspend fun getRatingWithMovies(id: String) =
        repository.getRatingWithMovies(id)

    suspend fun getMovieWithCast(id: String) =
        repository.getMovieWithCast(id)

    suspend fun getActorWithFilmography(id: String) =
        repository.getActorWithFilmography(id)

    suspend fun getMovie(id: String) =
        repository.getMovie(id)

    private var movieUpdateJob: Job? = null

    fun updateMovie(movieDto: MovieDto) {
        movieUpdateJob?.cancel()
        movieUpdateJob = viewModelScope.launch {
            delay(500)
            repository.update(movieDto)
            movieUpdateJob = null
        }
    }

    fun resetDatabase() {
        viewModelScope.launch {
            repository.resetDatabase()
        }
    }
}