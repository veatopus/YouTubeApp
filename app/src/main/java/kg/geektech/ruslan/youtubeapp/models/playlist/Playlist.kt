package kg.geektech.ruslan.youtubeapp.models.playlist

import kg.geektech.ruslan.youtubeapp.models.playlists.Thumbnails

data class Playlist(
    var items: MutableList<Item>
)

data class Item(
    var snippet: Snippet
)

data class Snippet(
    var title: String,
    var description: String,
    var publishedAt: String,
    var thumbnails: Thumbnails
)