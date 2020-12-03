package kg.geektech.ruslan.youtubeapp.ui.playlists

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kg.geektech.ruslan.youtubeapp.core.BaseViewModel
import kg.geektech.ruslan.youtubeapp.data.models.playlists.PlaylistItem
import kg.geektech.ruslan.youtubeapp.data.network.Status
import kg.geektech.ruslan.youtubeapp.data.repository.YoutubeRepository

class PlaylistsViewModel(private var repository: YoutubeRepository) : BaseViewModel() {
    val mutableLiveDataListPlaylistItem =
        MutableLiveData<MutableList<PlaylistItem>>(mutableListOf())

    fun fetchPlaylists(pageToken: String?) {
        val newData = mutableLiveDataListPlaylistItem.value
        repository.fetchPlaylists(pageToken).observeForever { result ->
            Log.d("gggghfhfhad", "fetchPlaylists: ${result.status} \n${result.data}")

            if (result.status == Status.SUCCESS)
                result.data?.forEach { playlists ->
                    playlists.items?.let { newData?.addAll(it) }
                }

            when (result.status) {
                Status.LOADING -> isLoading.value = true
                else -> isLoading.value = false
            }
            mutableLiveDataListPlaylistItem.value = newData!!
        }
    }

}
