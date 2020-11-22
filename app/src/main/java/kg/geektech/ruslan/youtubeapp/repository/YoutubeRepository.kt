package kg.geektech.ruslan.youtubeapp.repository

import androidx.lifecycle.liveData
import kg.geektech.ruslan.youtubeapp.data.local.dao.PlayListDao
import kg.geektech.ruslan.youtubeapp.data.network.Resource
import kg.geektech.ruslan.youtubeapp.data.network.YoutubeApi
import kotlinx.coroutines.Dispatchers

const val channel = "UC2Ru64PHqW4FxoP0xhQRvJg"
const val key = "AIzaSyAx8p70xc-SuyvmfhLZbCCJNiqQOQG0nj0"
const val part = "snippet,contentDetails"

class YoutubeRepository(private var api: YoutubeApi, private var dao: PlayListDao) {

    fun fetchPlaylists(pageToken: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        if (dao.getAll()?.isNotEmpty()!!)
            emit(Resource.success(dao.getAll()!![0]))
        else {
            try {
                val data = api.fetchPlaylists(part, pageToken, key, channel)
                dao.insert(data)
                emit(Resource.success(data))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error"))
            }
        }
    }

    fun fetchPlaylistByIdFromNetwork(id: String, pageToken: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = api.fetchPlaylistById(part, pageToken, id, key)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error"))
        }
    }

}
