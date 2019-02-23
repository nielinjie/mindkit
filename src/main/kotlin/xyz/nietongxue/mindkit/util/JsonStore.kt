package xyz.nietongxue.mindkit.util

import com.google.gson.Gson
import com.google.gson.JsonParser


object JsonStore {
    inline fun <reified T : Any> save(list: List<T>): String {
        val gson = Gson()
        return gson.toJson(list.map {v ->
            gson.toJsonTree(v).asJsonObject.also {
                it.add("_type", gson.toJsonTree(v::class.java.name)) }
        })
    }

    fun  load(json: String): List<Any> {
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

