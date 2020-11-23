package kg.geektech.ruslan.youtubeapp.data.local.client

import android.content.Context
import androidx.room.Room
import kg.geektech.ruslan.youtubeapp.data.local.dao.PlayListDao
import kg.geektech.ruslan.youtubeapp.data.local.database.PlaylistDatabase

class DatabaseClient {

    internal fun provideDatabase(context: Context): PlaylistDatabase {
        return Room.databaseBuilder(
            context,
            PlaylistDatabase::class.java,
            "playlist.database"
        )
            .build()
    }

    fun providePlaylistDao(database: PlaylistDatabase): PlayListDao? = database.playlistDao()
}
