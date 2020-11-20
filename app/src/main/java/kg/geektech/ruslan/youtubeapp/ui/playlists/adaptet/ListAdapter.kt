package kg.geektech.ruslan.youtubeapp.ui.playlists.adaptet

import android.view.View
import kg.geektech.ruslan.youtubeapp.R
import kg.geektech.ruslan.youtubeapp.core.BaseAdapter
import kg.geektech.ruslan.youtubeapp.core.loadImage
import kg.geektech.ruslan.youtubeapp.data.models.playlists.PlaylistItem
import kotlinx.android.synthetic.main.item_playlist.view.*

class ListAdapter: BaseAdapter<PlaylistItem>(R.layout.item_playlist, mutableListOf()){

    override fun onBind(view: View, model: PlaylistItem) {
        view.item_playlist_text_view_title_playList.text = model.snippet?.title
        view.item_playlist_text_view_amount_video.text = model.contentDetails?.itemCount
        model.snippet?.thumbnails?.medium?.url?.let { view.item_playlist_image_playlist.loadImage(it) }
    }
}