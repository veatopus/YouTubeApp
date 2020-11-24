package kg.geektech.ruslan.youtubeapp.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist.DetailsItem
import java.lang.reflect.Type

object DetailsPlaylistConverter {

    private val gson = Gson()
    private val type: Type = object : TypeToken<MutableList<DetailsItem>>() {}.type

    @TypeConverter
    @JvmStatic
    fun daysOfWeekToString(playlistItems: MutableList<DetailsItem>?): String? =
        gson.toJson(playlistItems, type)

    @TypeConverter
    @JvmStatic
    fun stringToDaysOfWeek(playlistItems: String?): MutableList<DetailsItem>? =
        gson.fromJson(playlistItems, type)

}