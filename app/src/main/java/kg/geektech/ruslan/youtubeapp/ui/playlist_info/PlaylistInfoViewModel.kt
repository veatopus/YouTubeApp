package kg.geektech.ruslan.youtubeapp.ui.playlist_info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist.DetailsPlaylist
import kg.geektech.ruslan.youtubeapp.data.network.Status
import kg.geektech.ruslan.youtubeapp.repository.YoutubeRepository

class PlaylistInfoViewModel(var repository: YoutubeRepository) : ViewModel() {
    val isLoading = MutableLiveData<Boolean>(false)
    var playListItems = MutableLiveData<DetailsPlaylist>()

    fun fetchPlaylistById(playlistApi: String, pageToken: String?, playlistDao: String) {
        repository.fetchDetailsPlaylistById(playlistApi, pageToken, playlistDao)
            .observeForever { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        playListItems.value = resource.data!!
                        isLoading.value = false
                        Log.d(
                            "getPlayListSuccess",
                            "fetchPlaylistById: ${resource.data}"
                        )
                    }

                    Status.ERROR -> {
                        isLoading.value = false
                        Log.e(
                            "getPlayListError",
                            "fetchPlaylistById: ${resource.message}"
                        )
                    }

                    Status.LOADING -> {
                        isLoading.value = true
                        Log.d(
                            "getPlayListLoading",
                            "fetchPlaylistById: ${resource.message}"
                        )
                    }
                }
            }
    }
}
