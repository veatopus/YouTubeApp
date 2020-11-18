package kg.geektech.ruslan.youtubeapp.ui.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kg.geektech.ruslan.youtubeapp.data.models.playlists.Playlists
import kg.geektech.ruslan.youtubeapp.data.network.Resource
import kg.geektech.ruslan.youtubeapp.repository.YoutubeRepository

class PlaylistsViewModel : ViewModel() {

    fun fetchPlaylists(): LiveData<Resource<Playlists>> {
        return YoutubeRepository().fetchPlaylistsFromNetwork()
    }
}