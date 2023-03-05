package lewis.libby.module4_example.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    indices = [
        Index(value = ["ratingId"])
    ],
)
data class Movie(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var title: String,
    var description: String,
    var ratingId: String,
)