package kg.geektech.ruslan.youtubeapp.ui.playlists

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kg.geektech.ruslan.youtubeapp.data.models.playlists.PlaylistItem
import kg.geektech.ruslan.youtubeapp.data.network.Status
import kg.geektech.ruslan.youtubeapp.repository.YoutubeRepository

class PlaylistsViewModel(private var repository: YoutubeRepository) : ViewModel() {
    val mutableLiveDataListPlaylistItem =
        MutableLiveData<MutableList<PlaylistItem>>(mutableListOf())

    fun fetchPlaylists(pageToken: String?) {
        val newData = mutableLiveDataListPlaylistItem.value
        repository.fetchPlaylists(pageToken).observeForever { result ->
            if (result.status == Status.SUCCESS) {
                result.data?.forEach { playlists ->
                    playlists.items?.let { newData?.addAll(it) }
                }
            }
            mutableLiveDataListPlaylistItem.value = newData!!
        }
    }

}