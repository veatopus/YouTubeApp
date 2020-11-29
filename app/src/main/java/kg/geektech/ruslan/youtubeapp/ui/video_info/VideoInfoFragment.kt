package kg.geektech.ruslan.youtubeapp.ui.video_info

import android.view.LayoutInflater
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kg.geektech.ruslan.youtubeapp.R
import kg.geektech.ruslan.youtubeapp.core.BaseFragment
import kg.geektech.ruslan.youtubeapp.core.gone
import kg.geektech.ruslan.youtubeapp.core.visible
import kg.geektech.ruslan.youtubeapp.databinding.VideoInfoFragmentBinding
import org.koin.android.ext.android.inject

class VideoInfoFragment :
    BaseFragment<VideoInfoViewModel, VideoInfoFragmentBinding>(R.layout.video_info_fragment) {

    private var videoId: String? = null


    override fun getViewModule(): VideoInfoViewModel = inject<VideoInfoViewModel>().value

    override fun getViewBinding(): VideoInfoFragmentBinding = VideoInfoFragmentBinding.inflate(
        LayoutInflater.from(requireContext())
    )

    override fun setUpView(binding: VideoInfoFragmentBinding) {
        binding.textViewDescription.text = arguments?.getString(DESCRIPTION)
        binding.textViewTitle.text = arguments?.getString(TITLE)

        videoId = arguments?.getString(VIDEO_ID)

        binding.youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                videoId?.let { youTubePlayer.loadVideo(it, 0f) }
            }
        })
    }

    override fun setUpViewModelObs(viewModel: VideoInfoViewModel) {

    }

    override fun progress(isProgress: Boolean) {
        if (isProgress) binding?.progressBar?.visible()
        else binding?.progressBar?.gone()
    }

    companion object {
        const val DESCRIPTION = "DESCRIPTION"
        const val TITLE = "TITLE"
        const val VIDEO_ID = "VIDEO_ID"
    }
}