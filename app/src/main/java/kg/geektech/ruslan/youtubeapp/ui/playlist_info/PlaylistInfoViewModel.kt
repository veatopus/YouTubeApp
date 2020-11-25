package kg.geektech.ruslan.youtubeapp.ui.playlist_info

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
                if (resource.status == Status.SUCCESS) {
                    playListItems.value = resource.data!!
                    isLoading.value = false
                }
                else if (resource.status == Status.LOADING) { isLoading.value = true }
            }
    }
}
