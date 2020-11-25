package kg.geektech.ruslan.youtubeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist.DetailsPlaylist

@Dao
interface DetailPlayListDao{

    @Query("SELECT * FROM detailsplaylist")
    suspend fun getAll(): MutableList<DetailsPlaylist>?

    @Query("SELECT * FROM detailsplaylist WHERE id = :id")
    suspend fun getDetailsPlaylistById(id: Int): DetailsPlaylist?

    @Insert
    suspend fun insert(playlists: DetailsPlaylist)

}