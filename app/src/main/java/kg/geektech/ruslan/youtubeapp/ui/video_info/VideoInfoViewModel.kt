package kg.geektech.ruslan.youtubeapp.ui.video_info

import androidx.lifecycle.MutableLiveData
import kg.geektech.ruslan.youtubeapp.core.BaseViewModel
import kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist.DetailsItem
import kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist.DetailsPlaylist
import kg.geektech.ruslan.youtubeapp.data.repository.YoutubeRepository

class VideoInfoViewModel() : BaseViewModel() {
    val currentVideo = MutableLiveData<DetailsItem>()
    var firsVideoId: String? = null
    var detailsPlaylist = MutableLiveData<DetailsPlaylist>()

    fun setUpPlaylistData(detailsPlaylist: DetailsPlaylist) {
        if (this.detailsPlaylist.value == null) {
            if (detailsPlaylist.items.isNotEmpty()) currentVideo.value = detailsPlaylist.items[0]
            this.detailsPlaylist.value = detailsPlaylist
        }
    }

    fun setUpFirstVideoId(videoId: String?) {
        if (firsVideoId == null)
            firsVideoId = videoId
    }

    fun changeVideo(detailItem: DetailsItem) {
        currentVideo.value = detailItem
    }

    fun changeLastSecond(time: Float) {
        currentVideo.value?.let {
            it.startTime = time
        }
    }
}