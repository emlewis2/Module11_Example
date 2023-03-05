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
data class RatingScreen(
    val id: String
): Screen

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MovieRepository = MovieDatabaseRepository(application)

    var screen by mutableStateOf<Screen>(RatingList)
        private set

    val ratingsFlow = repository.ratingsFlow
    val moviesFlow = repository.moviesFlow
    val actorsFlow = repository.actorsFlow

    suspend fun getRatingWithMovies(id: String) =
        repository.getRatingWithMovies(id)

    fun switchTo(screen: Screen) {
        this.screen = screen
    }

    fun resetDatabase() {
        viewModelScope.launch {
            repository.resetDatabase()
        }
    }
}