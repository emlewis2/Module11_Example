package lewis.libby.module4_example.screens

import androidx.compose.runtime.Composable
import lewis.libby.module4_example.components.SimpleText
import lewis.libby.module4_example.repository.ActorDto

@Composable
fun ActorList(
    actors: List<ActorDto>
) {
    SimpleText(text = "Actors")
    actors.forEach {
        SimpleText(text = it.name)
    }
}