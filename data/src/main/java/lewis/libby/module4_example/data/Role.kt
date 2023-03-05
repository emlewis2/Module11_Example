package lewis.libby.module4_example.data

import androidx.room.Entity

@Entity(
    primaryKeys = ["actorId", "movieId"],
)
data class Role(
    var movieId: String,
    var actorId: String,
    var character: String,
    var orderInCredits: Int,
)