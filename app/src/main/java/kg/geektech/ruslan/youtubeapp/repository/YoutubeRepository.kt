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
            updateDataPlaylistDao(pageToken)
            emit(Resource.success(playListDao.getAllPlaylists()))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error"))
        }
    }

    private suspend fun updateDataPlaylistDao(pageToken: String?) {
        var playlists: MutableList<Playlists>? = null
        try {
            playlists = fetchPlaylistsByNetwork(pageToken, mutableListOf())
        } catch (ignore: Exception) {
        }

        if (playlists != null) {
            playListDao.deleteAllPlaylists()
            playlists.forEach { playListDao.insertPlaylists(it) }
        }
    }

    private suspend fun fetchPlaylistsByNetwork(
        pageToken: String?,
        data: MutableList<Playlists>
    ): MutableList<Playlists>? {
        Log.d("dghf", "fetchPlaylistsByNetwork: ")
        val newData = api.fetchPlaylists(part, pageToken, key, channelId)
        if (newData != null) {
            newData.also {
                data.add(it)
                return if (it.nextPageToken != null) fetchPlaylistsByNetwork(
                    it.nextPageToken,
                    data
                )
                else data
            }
        } else return null
    }

    fun fetchDetailsPlaylistById(playlistApiId: String, pageToken: String?, playlistDaoId: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                updateDAtaDetailPlaylist(pageToken, playlistApiId)
                emit(Resource.success(data = detailPlayListDao.getDetailsPlaylistById(playlistDaoId)))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.error(data = null, message = e.message ?: "Error"))
            }
        }

    private suspend fun updateDAtaDetailPlaylist(
        pageToken: String?,
        playlistApiId: String
    ) {
        var data: DetailsPlaylist? = null
        try {
            data = fetchDetailPlaylistsByNetwork(pageToken, playlistApiId, null)
        } catch (ignore: Exception) {

        } finally {
            if (data != null) {
                data.playlistApiId = data.items[0].snippet.playlistId
                detailPlayListDao.insertDetailsPlaylist(data)
            }
        }
    }

    private suspend fun fetchDetailPlaylistsByNetwork(
        pageToken: String?,
        playlistId: String,
        data: DetailsPlaylist?
    ): DetailsPlaylist? {
        val newData = api.fetchDetailPlaylistById(part, pageToken, playlistId, key)
        data?.let { newData?.items?.addAll(it.items) }
        return if (newData?.nextPageToken != null) fetchDetailPlaylistsByNetwork(
            newData.nextPageToken,
            playlistId,
            newData
        )
        else newData
    }

}
