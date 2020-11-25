package kg.geektech.ruslan.youtubeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kg.geektech.ruslan.youtubeapp.data.models.playlists.Playlists

@Dao
interface PlayListDao {

    @Query("SELECT * FROM playlists")
    suspend fun getAllPlaylists(): MutableList<Playlists>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylists(playlists: Playlists)

    @Query("DELETE FROM playlists")
    suspend fun deleteAllPlaylists()
}
