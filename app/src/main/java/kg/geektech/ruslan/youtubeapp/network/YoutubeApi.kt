package kg.geektech.ruslan.youtubeapp.network

import kg.geektech.ruslan.youtubeapp.models.playlist.Playlist
import kg.geektech.ruslan.youtubeapp.models.playlists.Playlists
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApi {
    @GET("youtube/v3/playlists")
    fun fetchPlaylists(
        @Query("part") part: String,
        @Query("key") key: String,
        @Query("channelId") channelId: String,
        @Query("maxResult") maxResult: String
    ): Call<Playlists>

    @GET("youtube/v3/playlistItems")
    fun fetchPlaylistById(
        @Query("part") part: String,
        @Query("playlistId") channelId: String,
        @Query("key") key: String
    ): Call<Playlist>
}
