package kg.geektech.ruslan.youtubeapp.ui.video_info

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kg.geektech.ruslan.youtubeapp.R
import kg.geektech.ruslan.youtubeapp.core.BaseAdapter
import kg.geektech.ruslan.youtubeapp.core.BaseFragment
import kg.geektech.ruslan.youtubeapp.core.gone
import kg.geektech.ruslan.youtubeapp.core.visible
import kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist.DetailsItem
import kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist.DetailsPlaylist
import kg.geektech.ruslan.youtubeapp.databinding.VideoInfoFragmentBinding
import kg.geektech.ruslan.youtubeapp.ui.bottom_sheet_dialog.BottomSheetPlaylistDialogFragment
import org.koin.android.ext.android.inject

class VideoInfoFragment :
    BaseFragment<VideoInfoViewModel, VideoInfoFragmentBinding>(R.layout.video_info_fragment),
    BaseAdapter.IBaseAdapterClickListener<DetailsItem> {

    private var youTubePlayer: YouTubePlayer? = null
    private var bottomSheetPlaylistDialogFragment: BottomSheetPlaylistDialogFragment? = null

    override fun getViewModule(): VideoInfoViewModel = inject<VideoInfoViewModel>().value

    override fun getViewBinding(): VideoInfoFragmentBinding = VideoInfoFragmentBinding.inflate(
        LayoutInflater.from(requireContext())
    )

    override fun setUpView(binding: VideoInfoFragmentBinding) {
        setUpData(arguments)
        setUpYouTubePlayer()
        setUpBtnShowPlaylist()
    }

    private fun setUpBtnShowPlaylist() {
        binding?.btnShowPlaylist?.setOnClickListener {
            bottomSheetPlaylistDialogFragment?.let {
                showSheet()
            }
        }
    }

    private fun setUpBottomSheet(detailsPlaylist: DetailsPlaylist) {
        bottomSheetPlaylistDialogFragment =
            BottomSheetPlaylistDialogFragment(detailsPlaylist, this)
    }

    private fun showSheet() {
        bottomSheetPlaylistDialogFragment?.show(
            childFragmentManager,
            bottomSheetPlaylistDialogFragment!!.tag
        )
    }

    private fun setUpYouTubePlayer() {
        binding?.youtubePlayer?.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                this@VideoInfoFragment.youTubePlayer = youTubePlayer
                mViewModule?.firsVideoId?.let { youTubePlayer.loadVideo(it, 0f) }
            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                mViewModule?.changeLastSecond(second)
            }
        })
    }

    private fun updateData(currentVideo: DetailsItem, startVideoSecond: Float) {
        binding?.textViewDescription?.text = currentVideo.snippet.description
        binding?.textViewTitle?.text = currentVideo.snippet.title

        currentVideo.snippet.resourceId?.videoId?.let {
            youTubePlayer?.loadVideo(
                it,
                startVideoSecond
            )
        }
    }

    private fun setUpData(arguments: Bundle?) {
        arguments?.let { arg ->
            mViewModule?.setUpPlaylistData(arg.getSerializable(VIDEOS) as DetailsPlaylist)
            mViewModule?.setUpFirstVideoId(arg.getString(FIRST_VIDEO_ID))
        }
    }

    override fun setUpViewModelObs(viewModel: VideoInfoViewModel) {
        viewModel.currentVideo.observe(requireActivity(), Observer {
            updateData(it, it.startTime)
        })
        viewModel.detailsPlaylist.observe(requireActivity(), Observer {
            setUpBottomSheet(it)
            this.showSheet()
        })
    }

    override fun progress(isProgress: Boolean) {
        if (isProgress) binding?.progressBar?.visible()
        else binding?.progressBar?.gone()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mViewModule?.currentVideo?.value?.startTime?.let { outState.putFloat("second", it) }
    }

    override fun onRestoreInstanceState(saveInstanceState: Bundle) {
        super.onRestoreInstanceState(saveInstanceState)
        mViewModule?.changeLastSecond(saveInstanceState.getFloat("second"))
    }

    companion object {
        const val VIDEOS = "VIDEO_ID"
        const val FIRST_VIDEO_ID = "FIRST_VIDEO_ID"
    }

    override fun onClick(model: DetailsItem) {
        mViewModule?.changeVideo(model)
    }
}