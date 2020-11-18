package kg.geektech.ruslan.youtubeapp.ui.playlist_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kg.geektech.ruslan.youtubeapp.data.models.playlist.Playlist
import kg.geektech.ruslan.youtubeapp.data.network.Resource
import kg.geektech.ruslan.youtubeapp.repository.YoutubeRepository

class PlaylistInfoViewModel : ViewModel() {

    fun fetchPlaylistById(id: String): LiveData<Resource<Playlist>> {
        return YoutubeRepository().fetchPlaylistByIdFromNetwork(id)
    }
}