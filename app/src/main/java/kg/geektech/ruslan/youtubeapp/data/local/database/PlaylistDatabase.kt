package kg.geektech.ruslan.youtubeapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kg.geektech.ruslan.youtubeapp.data.local.dao.PlayListDao
import kg.geektech.ruslan.youtubeapp.data.models.playlists.PlaylistItem
import kg.geektech.ruslan.youtubeapp.data.models.playlists.Playlists

@Database(entities = [Playlists::class], version = 1)
abstract class PlaylistDatabase : RoomDatabase() {
    abstract fun playlistDao(): PlayListDao?
}