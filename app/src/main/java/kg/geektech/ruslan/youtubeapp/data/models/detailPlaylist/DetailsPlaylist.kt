package kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist

import androidx.room.Entity
import androidx.room.PrimaryKey
import kg.geektech.ruslan.youtubeapp.data.models.playlists.Thumbnails

@Entity
data class DetailsPlaylist(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
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
    var thumbnails: Thumbnails
)