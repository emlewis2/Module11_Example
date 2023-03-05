package lewis.libby.module4_example.repository

import lewis.libby.module4_example.data.Movie

data class MovieDto(
    val id: String,
    val title: String,
    val description: String,
    val ratingId: String,
)

internal fun Movie.toDto() =
    MovieDto(id = id, title = title, description = description, ratingId = ratingId)
internal fun MovieDto.toEntity() =
    Movie(id = id, title = title, description = description, ratingId = ratingId)