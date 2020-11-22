package kg.geektech.ruslan.youtubeapp.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.geektech.ruslan.youtubeapp.data.models.playlists.Snippet
import java.lang.reflect.Type

class SnippetConverter {
    companion object {
        private val gson = Gson()
        private val type: Type = object : TypeToken<Snippet?>() {}.type

        @TypeConverter
        fun fromSnippet(snippet: Snippet?): String? = gson.toJson(snippet, type)

        @TypeConverter
        fun fromGson(string: String?): Snippet? = gson.fromJson(string, type)

    }
}