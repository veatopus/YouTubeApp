package kg.geektech.ruslan.youtubeapp.data.models.playlists

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kg.geektech.ruslan.youtubeapp.data.local.converters.ContentDetailsConverter
import kg.geektech.ruslan.youtubeapp.data.local.converters.PlaylistItemsConverter
import kg.geektech.ruslan.youtubeapp.data.local.converters.SnippetConverter

@Entity
data class Playlists(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var nextPageToken: String,
    @TypeConverters(PlaylistItemsConverter::class)
    var items: MutableList<PlaylistItem>? = null
)

data class PlaylistItem(
    var id: String? = null,
    @TypeConverters(SnippetConverter::class)
    var snippet: Snippet? = null,
    @TypeConverters(ContentDetailsConverter::class)
    var contentDetails: ContentDetails? = null
)

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