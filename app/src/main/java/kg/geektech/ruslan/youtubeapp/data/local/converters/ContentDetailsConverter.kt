package kg.geektech.ruslan.youtubeapp.data.local.converters

import androidx.room.TypeConverter
import kg.geektech.ruslan.youtubeapp.data.models.playlists.ContentDetails

class ContentDetailsConverter {
    companion object {

        @TypeConverter
        fun fromContentDetails(contentDetails: ContentDetails?): String? = contentDetails?.itemCount

        @TypeConverter
        fun fromGson(content: String?): ContentDetails? = ContentDetails(content)
    }
}