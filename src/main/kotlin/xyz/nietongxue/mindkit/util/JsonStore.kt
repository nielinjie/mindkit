package xyz.nietongxue.mindkit.util

import com.google.gson.Gson
import com.google.gson.JsonParser
import java.io.File


class FolderJsonStore(val base: File) {
    init {
        require(base.isDirectory)
    }

    inline fun <reified T : Any> save(name: String, list: List<T>) {
        val file = File(base, "$name.json")
        FileJsonStore(file).save(list)
    }

    fun load(name: String): List<Any> {
        val file = File(base, "$name.json")
        return FileJsonStore(file).load()
    }
}

class FileJsonStore(val file: File) {
    init {
        require(file.isFile && file.canRead() && file.canWrite())
    }

    inline fun <reified T : Any> save(list: List<T>) {
        file.writeText(JsonStore.save(list))
    }

    fun load(): List<Any> {
        return JsonStore.load(file.readText())
    }
}

object JsonStore {
    inline fun <reified T : Any> save(list: List<T>): String {
        val gson = Gson()
        return gson.toJson(list.map { v ->
            gson.toJsonTree(v).asJsonObject.also {
                it.add("_type", gson.toJsonTree(v::class.java.name))
            }
        })
    }

    fun load(json: String): List<Any> {
        val parser = JsonParser()
        val gson = Gson()
        val jsonArray = parser.parse(json).asJsonArray
        return jsonArray.map {
            val j = it.asJsonObject
            val typeName = j.get("_type").asString
            val clazz: Class<Any> =
                    try {
                        (Class.forName(typeName) as? Class<Any>)!!
                    } catch (_: Throwable) {
                        throw IllegalArgumentException("Unknown type: $typeName")
                    }
            val value = gson.fromJson(j, clazz)
            value
        }
    }
}

