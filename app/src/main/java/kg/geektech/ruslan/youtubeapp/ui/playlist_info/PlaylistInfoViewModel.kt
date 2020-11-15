package kg.geektech.ruslan.youtubeapp.ui.playlist_info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kg.geektech.ruslan.youtubeapp.models.playlist.Playlist
import kg.geektech.ruslan.youtubeapp.models.playlists.PlaylistItem
import kg.geektech.ruslan.youtubeapp.repository.YoutubeRepository

class PlaylistInfoViewModel : ViewModel() {

    fun fetchPlaylistById(id: String): MutableLiveData<Playlist?>{
        return YoutubeRepository().fetchPlaylistByIdFromNetwork(id)
    }
}