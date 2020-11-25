package kg.geektech.ruslan.youtubeapp.data.network

import kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist.DetailsPlaylist
import kg.geektech.ruslan.youtubeapp.data.models.playlists.Playlists
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApi {
    @GET("youtube/v3/playlists")
    suspend fun fetchPlaylists(
        @Query("part") part: String,
        @Query("pageToken") pageToken: String?,
        @Query("key") key: String,
        @Query("channelId") channelId: String
    ): Playlists?

    @GET("youtube/v3/playlistItems")
    suspend fun fetchDetailPlaylistById(
        @Query("part") part: String,
        @Query("pageToken") pageToken: String?,
        @Query("playlistId") playlistId: String,
        @Query("key") key: String
    ): DetailsPlaylist?
}
