package kg.geektech.ruslan.youtubeapp.ui.playlist_info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kg.geektech.ruslan.youtubeapp.core.BaseViewModel
import kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist.DetailsPlaylist
import kg.geektech.ruslan.youtubeapp.data.network.Status
import kg.geektech.ruslan.youtubeapp.data.repository.YoutubeRepository

class PlaylistInfoViewModel(private var repository: YoutubeRepository) : BaseViewModel() {
    var playListItems = MutableLiveData<DetailsPlaylist>()

    fun fetchPlaylistById(playlistId: String, pageToken: String?) {
        repository.fetchDetailsPlaylistById(pageToken, playlistId)
            .observeForever { resource ->
                if (resource.status == Status.SUCCESS)
                    playListItems.value = resource.data!!

                when (resource.status) {
                    Status.LOADING -> isLoading.value = true
                    else -> isLoading.value = false
                }
            }
    }
}
