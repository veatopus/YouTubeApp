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
    val isLoading = MutableLiveData<Boolean>(false)

    fun fetchPlaylists(pageToken: String?) {
        val newData = mutableLiveDataListPlaylistItem.value
        repository.fetchPlaylists(pageToken).observeForever { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    isLoading.value = false
                    for (i in result.data!!){
                        i.items?.let {
                            newData?.addAll(
                                it
                            )
                        }
                    }
                }
                Status.ERROR -> {
                    isLoading.value = false
                    Log.e("ololo", "fetchPlaylists: ${result.message}")
                }
                Status.LOADING -> {
                    isLoading.value = true
                    Log.d("ololo", "fetchPlaylists: ")
                }
            }
            mutableLiveDataListPlaylistItem.value = newData!!
        }
    }

}