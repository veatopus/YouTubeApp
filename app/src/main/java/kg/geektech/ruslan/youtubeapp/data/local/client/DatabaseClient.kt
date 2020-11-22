package kg.geektech.ruslan.youtubeapp.data.local.client

import android.app.Application
import android.content.Context
import androidx.room.Room
import kg.geektech.ruslan.youtubeapp.data.local.dao.PlayListDao
import kg.geektech.ruslan.youtubeapp.data.local.database.PlaylistDatabase

class DatabaseClient {

        fun provideDatabase(application: Application): PlaylistDatabase {
            return Room.databaseBuilder(
                application,
                PlaylistDatabase::class.java,
                "playlist.database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        fun providePlaylist(database: PlaylistDatabase): PlayListDao? {
            return database.playlistDao()
    }
}