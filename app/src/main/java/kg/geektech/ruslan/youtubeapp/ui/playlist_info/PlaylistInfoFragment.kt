package kg.geektech.ruslan.youtubeapp.ui.playlist_info

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kg.geektech.ruslan.youtubeapp.R
import kg.geektech.ruslan.youtubeapp.core.BaseAdapter
import kg.geektech.ruslan.youtubeapp.core.gone
import kg.geektech.ruslan.youtubeapp.core.loadImage
import kg.geektech.ruslan.youtubeapp.data.models.playlist.Item
import kg.geektech.ruslan.youtubeapp.data.network.Status
import kotlinx.android.synthetic.main.content_scrolling.*
import kotlinx.android.synthetic.main.item_playlist.view.*
import kotlinx.android.synthetic.main.playlist_info_fragment.*

class PlaylistInfoFragment : Fragment(), BaseAdapter.IBaseAdapterCallBack<Item>,
    BaseAdapter.IBaseAdapterClickListener {
    private lateinit var mViewModel: PlaylistInfoViewModel
    private var playListId: String? = null
    private val adapter = BaseAdapter(R.layout.item_playlist, mutableListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.playlist_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(PlaylistInfoViewModel::class.java)
        setUpData()
        setUpRecycler()
    }

    private fun setUpData() {
        playListId = arguments?.getString(KEY_PLAYLIST_ID)
        playListsInfoFragment_title.text = arguments?.getString(KEY_PLAYLIST_TITLE)
        playListsInfoFragment_description.text = arguments?.getString(KEY_PLAYLIST_DESCRIPTION)
    }

    private fun setUpRecycler() {
        adapter.setListener(this)
        playListsInfoFragment_recyclerview.adapter = adapter
        playListId?.let { it ->
            mViewModel.fetchPlaylistById(it).observe(requireActivity(), Observer {
                when(it.status){
                    Status.SUCCESS ->{
                        adapter.data = it?.data?.items!!
                        adapter.notifyDataSetChanged()
                    }
                    Status.ERROR -> {
                        Log.e("getPlayListError", "setUpRecycler: ${it.message}")
                    }
                    else -> {
                        Log.d("getPlayListError", "${it.status} ${it.message}")
                    }
                }
            })
        }

    }

    override fun onBind(view: View, model: Item) {
        view.item_playlist_text_view_hint.gone()
        view.item_playlist_text_view_title_playList.text = model.snippet.title
        view.item_playlist_text_view_amount_video.text = model.snippet.description
        view.item_playlist_image_playlist.loadImage(model.snippet.thumbnails.medium?.url!!)
    }

    override fun onClick(pos: Int) {

    }

    companion object {
        const val KEY_PLAYLIST_ID = "CEY_PLAYLIST_ID"
        const val KEY_PLAYLIST_TITLE = "CEY_PLAYLIST_TITLE"
        const val KEY_PLAYLIST_DESCRIPTION = "CEY_PLAYLIST_DESCRIPTION"
    }
}