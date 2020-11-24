package kg.geektech.ruslan.youtubeapp.repository

import androidx.lifecycle.liveData
import kg.geektech.ruslan.youtubeapp.data.local.dao.PlayListDao
import kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist.DetailsPlaylist
import kg.geektech.ruslan.youtubeapp.data.models.playlists.Playlists
import kg.geektech.ruslan.youtubeapp.data.network.Resource
import kg.geektech.ruslan.youtubeapp.data.network.YoutubeApi
import kotlinx.coroutines.Dispatchers

const val channelId = "UC2Ru64PHqW4FxoP0xhQRvJg"
const val key = "AIzaSyAx8p70xc-SuyvmfhLZbCCJNiqQOQG0nj0"
const val part = "snippet,contentDetails"

class YoutubeRepository(private var api: YoutubeApi, private var dao: PlayListDao) {

    fun fetchPlaylists(pageToken: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            var playlists: List<Playlists>? = dao.getAll()
            if (playlists.isNullOrEmpty()) {
                playlists = fetchPlaylistsByNetwork(pageToken, mutableListOf())
                playlists.forEach { dao.insert(it) }
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

    fun fetchDetailsPlaylistById(id: String, pageToken: String?)
            = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = fetchDetailPlaylistsByNetwork(
                        pageToken,
                        id,
                        mutableListOf()
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error"))
        }
    }

    private suspend fun fetchDetailPlaylistsByNetwork(
        pageToken: String?,
        playlistId: String,
        data: MutableList<DetailsPlaylist>
    ): MutableList<DetailsPlaylist> {
        api.fetchDetailPlaylistById(part, pageToken, playlistId, key).also {
            data.add(it)
            return if (it.nextPageToken != null) fetchDetailPlaylistsByNetwork(
                it.nextPageToken,
                playlistId,
                data
            )
            else data
        }
    }

}
