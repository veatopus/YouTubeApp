package kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist

import androidx.room.Entity
import androidx.room.PrimaryKey
import kg.geektech.ruslan.youtubeapp.data.models.playlists.Thumbnails

@Entity
data class DetailsPlaylist(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var playlistApiId: String? = null,
    var items: MutableList<DetailsItem>,
    var nextPageToken: String?
)

data class DetailsItem(
    var snippet: DetailsSnippet
)

data class DetailsSnippet(
    var title: String,
    var description: String,
    var publishedAt: String,
    var thumbnails: Thumbnails,
    var playlistId: String? = null,
    var resourceId: ResourceId? = null
)

data class ResourceId(
    var videoId: String? = null
)