package lewis.libby.module11_example.repository

import lewis.libby.module11_example.data.Rating
import lewis.libby.module11_example.data.RatingWithMovies

data class RatingDto(
    val id: String,
    val name: String,
    val description: String,
)

internal fun Rating.toDto() =
    RatingDto(id = id, name = name, description = description)
internal fun RatingDto.toEntity() =
    Rating(id = id, name = name, description = description)

data class RatingWithMoviesDto(
    val rating: RatingDto,
    val movies: List<MovieDto>,
)

internal fun RatingWithMovies.toDto() =
    RatingWithMoviesDto(
        rating = rating.toDto(),
        movies = movies.map { it.toDto() },
    )