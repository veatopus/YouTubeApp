package kg.geektech.ruslan.youtubeapp.repository

import android.util.Log
import androidx.lifecycle.liveData
import kg.geektech.ruslan.youtubeapp.data.local.dao.DetailPlayListDao
import kg.geektech.ruslan.youtubeapp.data.local.dao.PlayListDao
import kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist.DetailsPlaylist
import kg.geektech.ruslan.youtubeapp.data.models.playlists.Playlists
import kg.geektech.ruslan.youtubeapp.data.network.Resource
import kg.geektech.ruslan.youtubeapp.data.network.YoutubeApi
import kotlinx.coroutines.Dispatchers

const val channelId = "UC2Ru64PHqW4FxoP0xhQRvJg"
const val key = "AIzaSyAx8p70xc-SuyvmfhLZbCCJNiqQOQG0nj0"
const val part = "snippet,contentDetails"

class YoutubeRepository(
    private var api: YoutubeApi,
    private var playListDao: PlayListDao,
    private val detailPlayListDao: DetailPlayListDao
) {

    fun fetchPlaylists(pageToken: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            var playlists: List<Playlists>? = playListDao.getAll()
            if (playlists.isNullOrEmpty()) {
                playlists = fetchPlaylistsByNetwork(pageToken, mutableListOf())
                playlists.forEach { playListDao.insert(it) }
            }
            emit(Resource.success(playlists))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error"))
        }
    }

    private suspend fun fetchPlaylistsByNetwork(
        pageToken: String?,
        data: MutableList<Playlists>
    ): MutableList<Playlists> {
        api.fetchPlaylists(part, pageToken, key, channelId).also {
            data.add(it)
            return if (it.nextPageToken != null) fetchPlaylistsByNetwork(it.nextPageToken, data)
            else data
        }
    }

    fun fetchDetailsPlaylistById(playlistApiId: String, pageToken: String?, playlistDaoId: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                var data = detailPlayListDao.getDetailsPlaylistById(playlistDaoId)
                Log.d("ofdpojga", "fetchDetailsPlaylistById: $data")
                if (data == null) {
                    data = fetchDetailPlaylistsByNetwork(pageToken, playlistApiId, null)
                    data.playlistApiId = data.items[0].snippet.playlistId
                    detailPlayListDao.insert(data)
                }
                emit(Resource.success(data = data))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.error(data = null, message = e.message ?: "Error"))
            }
        }

    private suspend fun fetchDetailPlaylistsByNetwork(
        pageToken: String?,
        playlistId: String,
        data: DetailsPlaylist?
    ): DetailsPlaylist {
        val newData = api.fetchDetailPlaylistById(part, pageToken, playlistId, key)
        data?.let { newData.items.addAll(it.items) }
        return if (newData.nextPageToken != null) fetchDetailPlaylistsByNetwork(
            newData.nextPageToken,
            playlistId,
            newData
        )
        else newData
    }

}
