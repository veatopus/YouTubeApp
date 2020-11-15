package kg.geektech.ruslan.youtubeapp.ui.playlists

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kg.geektech.ruslan.youtubeapp.models.playlist.Playlist
import kg.geektech.ruslan.youtubeapp.models.playlists.PlaylistItem
import kg.geektech.ruslan.youtubeapp.models.playlists.Playlists
import kg.geektech.ruslan.youtubeapp.repository.YoutubeRepository

class PlaylistsViewModel : ViewModel() {

    fun fetchPlaylists(): MutableLiveData<Playlists?> {
        return YoutubeRepository().fetchPlaylistsFromNetwork()
    }
}