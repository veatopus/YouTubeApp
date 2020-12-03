package kg.geektech.ruslan.youtubeapp.data.models.playlists

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Playlists(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var nextPageToken: String? = null,
    var items: MutableList<PlaylistItem>? = null
)

data class PlaylistItem(
    var id: String? = null,
    var snippet: Snippet? = null,
    var contentDetails: ContentDetails? = null
) : Serializable

data class Snippet(
    var title: String? = null,
    var description: String? = null,
    var thumbnails: Thumbnails? = null
)

data class Thumbnails(
    var medium: Medium? = null
)

data class Medium(
    var url: String? = null
)

data class ContentDetails(
    var itemCount: String? = null
)