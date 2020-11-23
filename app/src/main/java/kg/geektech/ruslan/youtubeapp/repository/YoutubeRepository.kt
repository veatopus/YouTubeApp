package kg.geektech.ruslan.youtubeapp.repository

import androidx.lifecycle.liveData
import kg.geektech.ruslan.youtubeapp.data.local.dao.PlayListDao
import kg.geektech.ruslan.youtubeapp.data.models.playlists.Playlists
import kg.geektech.ruslan.youtubeapp.data.network.Resource
import kg.geektech.ruslan.youtubeapp.data.network.YoutubeApi
import kotlinx.coroutines.Dispatchers

const val channelId = "UC2Ru64PHqW4FxoP0xhQRvJg"
const val key = "AIzaSyAx8p70xc-SuyvmfhLZbCCJNiqQOQG0nj0"
const val part = "snippet,contentDetails"

class YoutubeRepository(private var api: YoutubeApi, private var dao: PlayListDao) {

    fun fetchPlaylists(pageToken: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        val data = mutableListOf<Playlists>()
        var playlist: Playlists?
        playlist = pageToken?.let { dao.getPlaylistByNextPageToken(it) }
        if (playlist == null) {
            playlist = api.fetchPlaylists(part, pageToken, key, channelId)
            dao.insert(playlist)
        }
        data.add(playlist)
        if (playlist.nextPageToken.isNotEmpty())
            fetchPlaylists(pageToken)

        try {
            emit(Resource.success(data))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error"))
        }
    }

    fun fetchDetailsPlaylistByIdFromNetwork(id: String, pageToken: String?) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = api.fetchPlaylistById(part, pageToken, id, key)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error"))
            }
        }

}
