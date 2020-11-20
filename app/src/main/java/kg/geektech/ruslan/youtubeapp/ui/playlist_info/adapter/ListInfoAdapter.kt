package kg.geektech.ruslan.youtubeapp.ui.playlist_info.adapter

import android.view.View
import kg.geektech.ruslan.youtubeapp.R
import kg.geektech.ruslan.youtubeapp.core.BaseAdapter
import kg.geektech.ruslan.youtubeapp.core.gone
import kg.geektech.ruslan.youtubeapp.core.loadImage
import kg.geektech.ruslan.youtubeapp.data.models.playlist.Item
import kotlinx.android.synthetic.main.item_playlist.view.*

class ListInfoAdapter: BaseAdapter<Item>(R.layout.item_playlist, mutableListOf()){

    override fun onBind(view: View, model: Item) {
        view.item_playlist_text_view_hint.gone()
        view.item_playlist_text_view_title_playList.text = model.snippet.title
        view.item_playlist_text_view_amount_video.text = model.snippet.description
        model.snippet.thumbnails.medium?.url?.let { view.item_playlist_image_playlist.loadImage(it) }
    }
}