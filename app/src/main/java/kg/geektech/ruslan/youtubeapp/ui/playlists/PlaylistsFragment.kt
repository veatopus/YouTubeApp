package kg.geektech.ruslan.youtubeapp.ui.playlists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kg.geektech.ruslan.youtubeapp.R
import kg.geektech.ruslan.youtubeapp.core.BaseAdapter
import kg.geektech.ruslan.youtubeapp.ui.playlist_info.PlaylistInfoFragment
import kg.geektech.ruslan.youtubeapp.ui.playlists.adapter.ListAdapter
import kotlinx.android.synthetic.main.playlists_fragment.*
import org.koin.android.ext.android.inject

class PlaylistsFragment : Fragment(),
    BaseAdapter.IBaseAdapterClickListener {

    private val mViewModel by inject<PlaylistsViewModel>()
    private val adapter = ListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.playlists_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecycler()
        setUpObs()
    }

    private fun setUpObs() {
        mViewModel.fetchPlaylists(null)
        mViewModel.mutableLiveDataListPlaylistItem.observe(requireActivity(), Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
        })
    }

    private fun setUpRecycler() {
        playListsFragment_recyclerview.adapter = adapter
        adapter.listener = this
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
                        it.putInt(
                            PlaylistInfoFragment.KEY_PLAYLIST_POSITION,
                            pos
                        )
                    }
            )
    }

}
