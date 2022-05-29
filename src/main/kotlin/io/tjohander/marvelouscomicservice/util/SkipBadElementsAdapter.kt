package io.tjohander.marvelouscomicservice.util

import com.squareup.moshi.*
import java.lang.reflect.Type

//https://stackoverflow.com/questions/54145519/moshi-adapter-to-skip-bad-objects-in-the-listt
class SkipBadListObjectsAdapterFactory : JsonAdapter.Factory {
    override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
        return if (annotations.isEmpty() && Types.getRawType(type) == List::class.java) {
            val elementType = Types.collectionElementType(type, List::class.java)
            val elementAdapter = moshi.adapter<Any>(elementType)
            SkipBadListObjectsAdapter(elementAdapter)
        } else {
            null
        }
    }

    private class SkipBadListObjectsAdapter<T : Any>(private val elementAdapter: JsonAdapter<T>) :
        JsonAdapter<List<T>>() {

        override fun fromJson(reader: JsonReader): List<T>? {
            val goodObjectsList = mutableListOf<T>()
            reader.beginArray()
            while (reader.hasNext()) {
                try {
                    val peeked = reader.peekJson()
                    elementAdapter.fromJson(peeked)?.let(goodObjectsList::add)
                } catch (e: JsonDataException) {
                    // Skip bad element ;)
                    println("Skipped bad element: ${e.message}" )
                }
                reader.skipValue()
            }
            reader.endArray()
            return goodObjectsList
        }

        override fun toJson(writer: JsonWriter, value: List<T>?) {
            throw UnsupportedOperationException("SkipBadListObjectsAdapter is only used to deserialize objects")
        }
    }
}