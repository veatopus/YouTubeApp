package kg.geektech.ruslan.youtubeapp.repository

import androidx.lifecycle.liveData
import kg.geektech.ruslan.youtubeapp.data.network.Resource
import kg.geektech.ruslan.youtubeapp.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers

class YoutubeRepository {
    private val channel = "UC2Ru64PHqW4FxoP0xhQRvJg"
    private val key = "AIzaSyAx8p70xc-SuyvmfhLZbCCJNiqQOQG0nj0"
    private val part = "snippet,contentDetails"

    private var api = RetrofitClient().instanceRetrofit()

    fun fetchPlaylistsFromNetwork() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = api.fetchPlaylists(
                        part = part,
                        key = key,
                        channelId = channel
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error"))
        }
    }

    fun fetchPlaylistByIdFromNetwork(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = api.fetchPlaylistById(
                        part = part,
                        key = key,
                        playlistId = id
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error"))
        }
    }

}