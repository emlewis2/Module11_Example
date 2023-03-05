package lewis.libby.module4_example.repository

import lewis.libby.module4_example.data.Actor

data class ActorDto(
    val id: String,
    val name: String,
)

internal fun Actor.toDto() =
    ActorDto(id = id, name = name)
internal fun ActorDto.toEntity() =
    Actor(id = id, name = name)