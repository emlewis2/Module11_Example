package lewis.libby.module4_example

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import lewis.libby.module4_example.repository.MovieDatabaseRepository
import lewis.libby.module4_example.repository.MovieRepository
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

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MovieRepository = MovieDatabaseRepository(application)

    private var screenStack: List<Screen> = listOf(MovieList)
        set(value) {
            field = value
            screen = value.lastOrNull()
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

    fun resetDatabase() {
        viewModelScope.launch {
            repository.resetDatabase()
        }
    }
}