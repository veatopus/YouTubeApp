package kg.geektech.ruslan.youtubeapp.ui.playlist_info

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kg.geektech.ruslan.youtubeapp.R
import kg.geektech.ruslan.youtubeapp.core.*
import kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist.DetailsItem
import kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist.DetailsPlaylist
import kg.geektech.ruslan.youtubeapp.data.models.playlists.PlaylistItem
import kg.geektech.ruslan.youtubeapp.databinding.PlaylistInfoFragmentBinding
import kg.geektech.ruslan.youtubeapp.ui.playlist_info.adapter.ListInfoAdapter
import kg.geektech.ruslan.youtubeapp.ui.video_info.VideoInfoFragment
import kotlinx.android.synthetic.main.content_scrolling.*
import kotlinx.android.synthetic.main.playlist_info_fragment.*
import org.koin.android.ext.android.inject

class PlaylistInfoFragment :
    BaseFragment<PlaylistInfoViewModel, PlaylistInfoFragmentBinding>(R.layout.playlist_info_fragment),
    BaseAdapter.IBaseAdapterClickListener<DetailsItem> {

    private var detailsPlaylist: DetailsPlaylist? = null
    private var playlistId: String? = null
    private val adapter = ListInfoAdapter()

    private fun updateItems(items: MutableList<DetailsItem>) {
        adapter.data = mutableListOf()
        adapter.data = items
        adapter.notifyDataSetChanged()
    }

    private fun setUpData() {
        val playlist: PlaylistItem? = arguments?.getSerializable(KEY_PLAYLIST) as PlaylistItem?
        playlistId = playlist?.id
        toolbar_layout.title = playlist?.snippet?.title
    }

    private fun setUpRecycler() {
        adapter.listener = this
        playListsInfoFragment_recyclerview.adapter = adapter
        playlistId?.let { playlistId ->
            mViewModule?.fetchPlaylistById(playlistId, null)
        }
    }

    override fun onClick(model: DetailsItem) {
        findNavController().navigate(R.id.videoInfoFragment, Bundle().also {
            it.putSerializable(VideoInfoFragment.VIDEOS, detailsPlaylist)
            it.putString(VideoInfoFragment.FIRST_VIDEO_ID, model.snippet.resourceId?.videoId)
        })
    }

    override fun setUpView(binding: PlaylistInfoFragmentBinding) {
        setUpData()
        setUpRecycler()
    }

    override fun setUpViewModelObs(viewModel: PlaylistInfoViewModel) {
        mViewModule?.playListItems?.observe(requireActivity(), Observer { detailsPlaylist ->
            this.detailsPlaylist = detailsPlaylist
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
        const val KEY_PLAYLIST = "KEY_PLAYLIST"
    }
}