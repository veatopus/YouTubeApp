package kg.geektech.ruslan.youtubeapp.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.geektech.ruslan.youtubeapp.data.models.playlists.PlaylistItem
import java.lang.reflect.Type

class PlaylistItemsConverter {
    companion object{
        private val gson = Gson()
        private val type: Type = object : TypeToken<MutableList<PlaylistItem>>() {}.type

        @TypeConverter
        fun daysOfWeekToString(playlistItems: MutableList<PlaylistItem>?): String? = gson.toJson(playlistItems, type)

        @TypeConverter
        fun stringToDaysOfWeek(playlistItems: String?): MutableList<PlaylistItem>? = gson.fromJson(playlistItems, type)
    }
}