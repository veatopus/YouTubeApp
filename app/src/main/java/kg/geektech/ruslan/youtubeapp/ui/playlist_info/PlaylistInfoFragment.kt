package kg.geektech.ruslan.youtubeapp.ui.playlist_info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kg.geektech.ruslan.youtubeapp.R
import kg.geektech.ruslan.youtubeapp.core.BaseAdapter
import kg.geektech.ruslan.youtubeapp.core.gone
import kg.geektech.ruslan.youtubeapp.core.loadImage
import kg.geektech.ruslan.youtubeapp.core.visible
import kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist.DetailsItem
import kg.geektech.ruslan.youtubeapp.ui.playlist_info.adapter.ListInfoAdapter
import kotlinx.android.synthetic.main.content_scrolling.*
import kotlinx.android.synthetic.main.playlist_info_fragment.*
import org.koin.android.ext.android.inject

class PlaylistInfoFragment : Fragment(),
    BaseAdapter.IBaseAdapterClickListener {

    private val mViewModel by inject<PlaylistInfoViewModel>()
    private var playlistId: String? = null
    private var playlistPosition: String? = null
    private val adapter = ListInfoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.playlist_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        setUpObserve()
        setUpData()
        setUpRecycler()
    }

    private fun setUpObserve() {
        mViewModel.playListItems.observe(requireActivity(), Observer { detailsPlaylist ->
            detailsPlaylist.items[0].snippet.thumbnails.medium?.url?.let { image_view.loadImage(it) }
            updateItems(detailsPlaylist.items)
        })
        mViewModel.isLoading.observe(requireActivity(), Observer {
            if (it) progress_bar.visible()
            else progress_bar.gone()
        })
    }

    private fun updateItems(items: MutableList<DetailsItem>) {
        adapter.data = mutableListOf()
        adapter.data = items
        adapter.notifyDataSetChanged()
    }

    private fun setUpData() {
        playlistId = arguments?.getString(KEY_PLAYLIST_ID)
        playlistPosition = arguments?.getString(KEY_PLAYLIST_POSITION)
        toolbar_layout.title = arguments?.getString(KEY_PLAYLIST_TITLE)
        toolbar_layout.title =
            (toolbar_layout.title as String?)?.plus(
                ("\n\n${arguments?.getString(
                    KEY_PLAYLIST_DESCRIPTION
                )}")
            )
    }

    private fun setUpRecycler() {
        adapter.listener = this
        playListsInfoFragment_recyclerview.adapter = adapter
        playlistId?.let { playlistId ->
            playlistPosition?.let {
                mViewModel.fetchPlaylistById(playlistId, null, it)
            }
        }
    }

    override fun onClick(pos: Int) {
        TODO("ADD ITEM IFO")
    }

    companion object {
        const val KEY_PLAYLIST_ID = "CEY_PLAYLIST_ID"
        const val KEY_PLAYLIST_TITLE = "CEY_PLAYLIST_TITLE"
        const val KEY_PLAYLIST_DESCRIPTION = "CEY_PLAYLIST_DESCRIPTION"
        const val KEY_PLAYLIST_POSITION = "CEY_PLAYLIST_POSITION"
    }
}