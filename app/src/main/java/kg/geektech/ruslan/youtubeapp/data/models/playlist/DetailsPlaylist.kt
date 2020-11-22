package kg.geektech.ruslan.youtubeapp.data.models.playlist

import kg.geektech.ruslan.youtubeapp.data.models.playlists.Thumbnails

data class DetailsPlaylist(
    var detailsItems: MutableList<DetailsItem>,
    var nextPageToken: String
)

data class DetailsItem(
    var detailsSnippet: DetailsSnippet
)

data class DetailsSnippet(
    var title: String,
    var description: String,
    var publishedAt: String,
    var thumbnails: Thumbnails
)