package kg.geektech.ruslan.youtubeapp.ui.playlists

import android.util.Log
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
            when (result.status) {
                Status.SUCCESS -> result.data?.forEach { playlist ->
                    playlist.items?.let {
                        mutableLiveDataListPlaylistItem.value?.addAll(
                            it
                        )
                    }
                }
                Status.ERROR -> Log.e("ololo", "fetchPlaylists: ${result.message}")
                Status.LOADING -> Log.d("ololo", "fetchPlaylists: ")
            }
            mutableLiveDataListPlaylistItem.value = newData
        }
    }

}