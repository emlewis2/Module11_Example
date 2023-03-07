package lewis.libby.module5_example.repository

import lewis.libby.module5_example.data.Actor
import lewis.libby.module5_example.data.ActorWithFilmography
import lewis.libby.module5_example.data.RoleWithMovie

data class ActorDto(
    val id: String,
    val name: String,
)

internal fun Actor.toDto() =
    ActorDto(id = id, name = name)
internal fun ActorDto.toEntity() =
    Actor(id = id, name = name)

data class ActorWithFilmographyDto(
    val actor: ActorDto,
    val filmography: List<RoleWithMovieDto>,
)

data class RoleWithMovieDto(
    val movie: MovieDto,
    val character: String,
    val orderInCredits: Int,
)

internal fun RoleWithMovie.toDto() =
    RoleWithMovieDto(
        movie = movie.toDto(),
        character = role.character,
        orderInCredits = role.orderInCredits,
    )

internal fun ActorWithFilmography.toDto() =
    ActorWithFilmographyDto(
        actor = actor.toDto(),
        filmography =
            rolesWithMovies.map {
                it.toDto()
            }
    )