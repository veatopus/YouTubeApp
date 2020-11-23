package kg.geektech.ruslan.youtubeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kg.geektech.ruslan.youtubeapp.data.models.playlists.Playlists

@Dao
interface PlayListDao {

    @Query("SELECT * FROM playlists WHERE nextPageToken = :pageToken")
    suspend fun getPlaylistByNextPageToken(pageToken: String): Playlists?

    @Insert
    fun insert(playlists: Playlists)
}
