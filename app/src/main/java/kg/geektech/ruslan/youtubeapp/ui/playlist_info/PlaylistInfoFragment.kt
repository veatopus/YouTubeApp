package kg.geektech.ruslan.youtubeapp.ui.playlist_info

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kg.geektech.ruslan.youtubeapp.R
import kg.geektech.ruslan.youtubeapp.core.*
import kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist.DetailsItem
import kg.geektech.ruslan.youtubeapp.databinding.PlaylistInfoFragmentBinding
import kg.geektech.ruslan.youtubeapp.ui.playlist_info.adapter.ListInfoAdapter
import kg.geektech.ruslan.youtubeapp.ui.video_info.VideoInfoFragment
import kotlinx.android.synthetic.main.content_scrolling.*
import kotlinx.android.synthetic.main.playlist_info_fragment.*
import org.koin.android.ext.android.inject

class PlaylistInfoFragment :
    BaseFragment<PlaylistInfoViewModel, PlaylistInfoFragmentBinding>(R.layout.playlist_info_fragment),
    BaseAdapter.IBaseAdapterClickListener {

    private var playlistId: String? = null
    private var playlistPosition: String? = null
    private val adapter = ListInfoAdapter()

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
                mViewModule?.fetchPlaylistById(playlistId, null, it)
            }
        }
    }

    override fun onClick(pos: Int) {
        findNavController().navigate(R.id.videoInfoFragment, Bundle().also {
            it.putString(VideoInfoFragment.VIDEO_ID, adapter.data[pos].snippet.resourceId?.videoId)
            it.putString(VideoInfoFragment.TITLE, adapter.data[pos].snippet.title)
            it.putString(VideoInfoFragment.DESCRIPTION, adapter.data[pos].snippet.description)
        })
    }

    override fun setUpView(binding: PlaylistInfoFragmentBinding) {
        setUpData()
        setUpRecycler()
    }

    override fun setUpViewModelObs(viewModel: PlaylistInfoViewModel) {
        mViewModule?.playListItems?.observe(requireActivity(), Observer { detailsPlaylist ->
            detailsPlaylist.items[0].snippet.thumbnails.medium?.url?.let { image_view.loadImage(it) }
            updateItems(detailsPlaylist.items)
        })
        mViewModule?.isLoading?.observe(requireActivity(), Observer {
            if (it) progress_bar.visible()
            else progress_bar.gone()
        })
    }

    override fun progress(isProgress: Boolean) {
        if (isProgress) binding?.progressBar?.visible()
        else binding?.progressBar?.gone()
    }

    override fun getViewModule(): PlaylistInfoViewModel = inject<PlaylistInfoViewModel>().value

    override fun getViewBinding(): PlaylistInfoFragmentBinding =
        PlaylistInfoFragmentBinding.inflate(
            LayoutInflater.from(requireContext())
        )

    companion object {
        const val KEY_PLAYLIST_ID = "CEY_PLAYLIST_ID"
        const val KEY_PLAYLIST_TITLE = "CEY_PLAYLIST_TITLE"
        const val KEY_PLAYLIST_DESCRIPTION = "CEY_PLAYLIST_DESCRIPTION"
        const val KEY_PLAYLIST_POSITION = "CEY_PLAYLIST_POSITION"
    }
}