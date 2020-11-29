package kg.geektech.ruslan.youtubeapp.ui.playlists

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kg.geektech.ruslan.youtubeapp.R
import kg.geektech.ruslan.youtubeapp.core.BaseAdapter
import kg.geektech.ruslan.youtubeapp.core.BaseFragment
import kg.geektech.ruslan.youtubeapp.core.gone
import kg.geektech.ruslan.youtubeapp.core.visible
import kg.geektech.ruslan.youtubeapp.databinding.PlaylistsFragmentBinding
import kg.geektech.ruslan.youtubeapp.ui.playlist_info.PlaylistInfoFragment
import kg.geektech.ruslan.youtubeapp.ui.playlists.adapter.ListAdapter
import kotlinx.android.synthetic.main.playlists_fragment.*
import org.koin.android.ext.android.inject

class PlaylistsFragment :
    BaseFragment<PlaylistsViewModel, PlaylistsFragmentBinding>(R.layout.playlists_fragment),
    BaseAdapter.IBaseAdapterClickListener {

    private val adapter = ListAdapter()

    override fun getViewModule(): PlaylistsViewModel = inject<PlaylistsViewModel>().value

    override fun getViewBinding(): PlaylistsFragmentBinding =
        PlaylistsFragmentBinding.inflate(LayoutInflater.from(requireContext()))

    override fun setUpView(binding: PlaylistsFragmentBinding) {
        setUpRecycler()
    }

    override fun setUpViewModelObs(viewModel: PlaylistsViewModel) {
        viewModel.fetchPlaylists(null)
        viewModel.mutableLiveDataListPlaylistItem.observe(requireActivity(), Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
        })
    }

    private fun setUpRecycler() {
        playListsFragment_recyclerview.adapter = adapter
        adapter.listener = this
    }

    override fun progress(isProgress: Boolean) {
        if (isProgress) binding?.progressBar?.visible()
        else binding?.progressBar?.gone()
    }

    override fun onClick(pos: Int) {
        findNavController()
            .navigate(
                R.id.playlistInfoFragment,
                Bundle()
                    .also {
                        it.putString(PlaylistInfoFragment.KEY_PLAYLIST_ID, adapter.data[pos].id)
                        it.putString(
                            PlaylistInfoFragment.KEY_PLAYLIST_TITLE,
                            adapter.data[pos].snippet?.title
                        )
                        it.putString(
                            PlaylistInfoFragment.KEY_PLAYLIST_DESCRIPTION,
                            adapter.data[pos].snippet?.description
                        )
                        it.putString(
                            PlaylistInfoFragment.KEY_PLAYLIST_POSITION,
                            adapter.data[pos].id
                        )
                    }
            )
    }
}
