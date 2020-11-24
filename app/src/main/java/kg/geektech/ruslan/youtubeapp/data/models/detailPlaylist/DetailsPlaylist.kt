package kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist

import kg.geektech.ruslan.youtubeapp.data.models.playlists.Thumbnails

data class DetailsPlaylist(
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