package kg.geektech.ruslan.youtubeapp.ui.playlists

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kg.geektech.ruslan.youtubeapp.R
import kg.geektech.ruslan.youtubeapp.core.BaseAdapter
import kg.geektech.ruslan.youtubeapp.core.BaseAdapter.IBaseAdapterCallBack
import kg.geektech.ruslan.youtubeapp.core.loadImage
import kg.geektech.ruslan.youtubeapp.models.playlists.PlaylistItem
import kg.geektech.ruslan.youtubeapp.ui.playlist_info.PlaylistInfoFragment
import kotlinx.android.synthetic.main.item_playlist.view.*
import kotlinx.android.synthetic.main.playlists_fragment.*

class PlaylistsFragment : Fragment(), IBaseAdapterCallBack<PlaylistItem>,
    BaseAdapter.IBaseAdapterClickListener {

    private lateinit var viewModel: PlaylistsViewModel

    private val adapter =
        BaseAdapter(R.layout.item_playlist, mutableListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.playlists_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlaylistsViewModel::class.java)
        setUpRecycler()
        viewModel.fetchPlaylists()
            .observe(requireActivity(), Observer {
                adapter.data = it?.items!!
                adapter.notifyDataSetChanged()
            })
    }

    private fun setUpRecycler() {
        playListsFragment_recyclerview.adapter = adapter
        adapter.setListener(this)
    }

    override fun onBind(view: View, model: PlaylistItem) {
        view.item_playlist_text_view_title_playList.text = model.snippet?.title
        view.item_playlist_text_view_amount_video.text = model.contentDetails?.itemCount
        model.snippet?.thumbnails?.medium?.url?.let { view.item_playlist_image_playlist.loadImage(it) }
    }

    override fun onClick(pos: Int) {
        findNavController()
            .navigate(
                R.id.playlistInfoFragment,
                Bundle()
                    .also {
                        it.putString(
                            PlaylistInfoFragment.KEY_PLAYLIST_ID,
                            adapter.data[pos].id
                        )
                        it.putString(
                            PlaylistInfoFragment.KEY_PLAYLIST_TITLE,
                            adapter.data[pos].snippet?.title
                        )
                        it.putString(
                            PlaylistInfoFragment.KEY_PLAYLIST_DESCRIPTION,
                            adapter.data[pos].snippet?.description
                        )
                    }
            )
    }

}
