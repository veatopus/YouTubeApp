package kg.geektech.ruslan.youtubeapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kg.geektech.ruslan.youtubeapp.models.playlist.Playlist
import kg.geektech.ruslan.youtubeapp.models.playlists.Playlists
import kg.geektech.ruslan.youtubeapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YoutubeRepository {
    private val channel = "UC8butISFwT-Wl7EV0hUK0BQ"
    private val key = "AIzaSyAx8p70xc-SuyvmfhLZbCCJNiqQOQG0nj0"
    private val part = "snippet,contentDetails"
    private val maxResult = "50"


    private var api = RetrofitClient().instanceRetrofit()

    fun fetchPlaylistsFromNetwork(): MutableLiveData<Playlists?> {
        val data = MutableLiveData<Playlists?>()
        api.fetchPlaylists(part, key, channel, maxResult).enqueue(object : Callback<Playlists?> {
            override fun onFailure(call: Call<Playlists?>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Playlists?>, response: Response<Playlists?>) {
                data.value = response.body()
            }

        })
        return data
    }

    fun fetchPlaylistByIdFromNetwork(id: String): MutableLiveData<Playlist?> {
        val data = MutableLiveData<Playlist?>()
        api.fetchPlaylistById(part, id, key).enqueue(object : Callback<Playlist> {
            override fun onFailure(call: Call<Playlist?>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Playlist?>, response: Response<Playlist?>) {
                data.value = response.body()
            }
        })
        return data
    }

}