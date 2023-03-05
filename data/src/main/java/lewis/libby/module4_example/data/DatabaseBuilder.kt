package lewis.libby.module4_example.data

import android.content.Context
import androidx.room.Room

fun createDao(context: Context) =
    Room.databaseBuilder(
        context,
        MovieDatabase::class.java,
        "MOVIES"
    )
// uncomment the following to see the SQL queries that are run
//        .setQueryCallback(
//            { sqlQuery, bindArgs ->
//                Log.d("!!!SQL", "SQL Query: $sqlQuery SQL Args: $bindArgs")
//            }, Executors.newSingleThreadExecutor()
//        )
        .build()
        .dao