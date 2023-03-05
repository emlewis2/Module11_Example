package lewis.libby.module4_example.repository

import android.content.Context
import lewis.libby.module4_example.data.createDao
import kotlinx.coroutines.flow.map

class MovieDatabaseRepository(context: Context): MovieRepository {
    private val dao = createDao(context)

    override val ratingsFlow =
        dao.getRatingsFlow()
            .map { ratings ->
                ratings.map { it.toDto() }
            }
    override val moviesFlow =
        dao.getMoviesFlow()
            .map { movies ->
                movies.map { it.toDto() }
            }
    override val actorsFlow =
        dao.getActorsFlow()
            .map { actors ->
                actors.map { it.toDto() }
            }

    override suspend fun getRatingWithMovies(id: String): RatingWithMoviesDto =
        dao.getRatingWithMovies(id).toDto()

    override suspend fun insert(movie: MovieDto) = dao.insert(movie.toEntity())
    override suspend fun insert(actor: ActorDto) = dao.insert(actor.toEntity())
    override suspend fun insert(rating: RatingDto) = dao.insert(rating.toEntity())

    override suspend fun update(movie: MovieDto) = dao.update(movie.toEntity())
    override suspend fun update(actor: ActorDto) = dao.update(actor.toEntity())
    override suspend fun update(rating: RatingDto) = dao.update(rating.toEntity())

    override suspend fun delete(movie: MovieDto) = dao.delete(movie.toEntity())
    override suspend fun delete(actor: ActorDto) = dao.delete(actor.toEntity())
    override suspend fun delete(rating: RatingDto) = dao.delete(rating.toEntity())

    override suspend fun resetDatabase() = dao.resetDatabase()
}