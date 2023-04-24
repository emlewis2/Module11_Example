package lewis.libby.module11_example.repository

import android.content.Context
import lewis.libby.module11_example.data.createDao

fun createRepository(context: Context) =
    MovieDatabaseRepository(
        createDao(context)
    )