package kg.geektech.ruslan.youtubeapp.ui.playlist_info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kg.geektech.ruslan.youtubeapp.core.BaseViewModel
import kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist.DetailsPlaylist
import kg.geektech.ruslan.youtubeapp.data.network.Status
import kg.geektech.ruslan.youtubeapp.repository.YoutubeRepository

class PlaylistInfoViewModel(var repository: YoutubeRepository) : BaseViewModel() {
    var playListItems = MutableLiveData<DetailsPlaylist>()

    fun fetchPlaylistById(playlistApi: String, pageToken: String?, playlistDao: String) {
        repository.fetchDetailsPlaylistById(playlistApi, pageToken, playlistDao)
            .observeForever { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        playListItems.value = resource.data!!
                        isLoading.value = false
                    }
                    Status.LOADING -> isLoading.value = true
                    Status.ERROR -> isLoading.value = false
                }
            }
    }
}
