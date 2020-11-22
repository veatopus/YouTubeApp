package kg.geektech.ruslan.youtubeapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kg.geektech.ruslan.youtubeapp.data.models.playlists.PlaylistItem
import kg.geektech.ruslan.youtubeapp.data.models.playlists.Playlists

@Dao
interface PlayListDao {

    @Query("SELECT * FROM playlists")
    suspend fun getAll(): List<Playlists?>?

    @Insert
    fun insert(playlists: Playlists)
}
