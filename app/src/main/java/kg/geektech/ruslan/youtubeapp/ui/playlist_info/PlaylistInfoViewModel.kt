package kg.geektech.ruslan.youtubeapp.ui.playlist_info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist.DetailsPlaylist
import kg.geektech.ruslan.youtubeapp.data.network.Status
import kg.geektech.ruslan.youtubeapp.repository.YoutubeRepository

class PlaylistInfoViewModel(var repository: YoutubeRepository) : ViewModel() {
    var playListItems = MutableLiveData<DetailsPlaylist>()
    private var id: String? = null

    fun fetchPlaylistById(playlistApi: String, pageToken: String?, playlistDao: Int) {
        this.id = playlistApi
        repository.fetchDetailsPlaylistById(playlistApi, pageToken, playlistDao).observeForever { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    playListItems.value = resource.data!!
                    Log.d(
                        "getPlayListSuccess",
                        "fetchPlaylistById: ${resource.data}"
                    )
                }

                Status.ERROR -> Log.e(
                    "getPlayListError",
                    "fetchPlaylistById: ${resource.message}"
                )

                Status.LOADING -> Log.d(
                    "getPlayListLoading",
                    "fetchPlaylistById: ${resource.message}"
                )
            }
        }
    }
}
