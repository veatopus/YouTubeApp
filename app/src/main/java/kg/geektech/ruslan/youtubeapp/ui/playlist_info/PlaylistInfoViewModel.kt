package kg.geektech.ruslan.youtubeapp.ui.playlist_info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kg.geektech.ruslan.youtubeapp.data.models.playlist.DetailsPlaylist
import kg.geektech.ruslan.youtubeapp.data.network.Status
import kg.geektech.ruslan.youtubeapp.repository.YoutubeRepository

class PlaylistInfoViewModel(var repository: YoutubeRepository) : ViewModel() {
    var playListItems = MutableLiveData<MutableList<DetailsPlaylist>>(mutableListOf())
    private var id: String? = null

    fun fetchPlaylistById(id: String, pageId: String?) {
        this.id = id
        repository.fetchPlaylistByIdFromNetwork(id, pageId).observeForever { resource ->
            val newData = playListItems.value
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let {
                        newData?.add(it)
                    }
                    if (resource.data?.nextPageToken != null) fetchPlaylistById(id, resource.data.nextPageToken)
                    Log.d("getPlayListSuccess", "fetchPlaylistById: $newData")
                }

                Status.ERROR -> Log.e(
                    "getPlayListError",
                    "setUpRecycler: ${resource.message}"
                )

                Status.LOADING -> Log.d(
                    "getPlayListLoading",
                    "setUpRecycler: ${resource.message}"
                )
            }
            playListItems.value = newData
        }
    }
}
