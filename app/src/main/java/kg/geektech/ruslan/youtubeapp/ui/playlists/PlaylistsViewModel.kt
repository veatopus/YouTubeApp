package kg.geektech.ruslan.youtubeapp.ui.playlists

import androidx.lifecycle.MutableLiveData
import kg.geektech.ruslan.youtubeapp.core.BaseViewModel
import kg.geektech.ruslan.youtubeapp.data.models.playlists.PlaylistItem
import kg.geektech.ruslan.youtubeapp.data.network.Status
import kg.geektech.ruslan.youtubeapp.repository.YoutubeRepository

class PlaylistsViewModel(private var repository: YoutubeRepository) : BaseViewModel() {
    val mutableLiveDataListPlaylistItem =
        MutableLiveData<MutableList<PlaylistItem>>(mutableListOf())

    fun fetchPlaylists(pageToken: String?) {
        val newData = mutableLiveDataListPlaylistItem.value
        repository.fetchPlaylists(pageToken).observeForever { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.forEach { playlists ->
                        playlists.items?.let { newData?.addAll(it) }
                    }
                    isLoading.value = false
                }
                Status.LOADING -> isLoading.value = true
                Status.ERROR -> isLoading.value = false

            }
            mutableLiveDataListPlaylistItem.value = newData!!
        }
    }

}
