package xyz.nietongxue.mindkit.model

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.lookup

data class Node(val id: String, val title: String, val labels: List<String>, val note: String?, val children: ArrayList<Node>) {
    companion object {
        fun fromJson(json: JsonObject): Node {
            val children: JsonArray<JsonObject> = (json["children"] as JsonObject?)?.array("attached") ?: JsonArray()
            return Node(
                    json["id"] as String,
                    json["title"] as String,
                    (json["labels"] as? JsonArray<*>)?.map { it.toString() } ?: emptyList(),
                    json.lookup<String?>("notes.plain.content")[0],
                    ArrayList(children.map {
                        Node.fromJson(it)
                    }.toList())
            )
        }
    }
}

data class Sheet(val root: Node) {
    companion object {
        fun fromJson(json: JsonObject): Sheet {
            return Sheet(Node.fromJson(json["rootTopic"] as JsonObject))
        }
    }
}

data class MindMap(val sheets: List<Sheet>) {
    companion object {
        fun fromJson(json: JsonArray<JsonObject>): MindMap {
            return MindMap(listOf(Sheet.fromJson(json.first())))
        }
    }
}