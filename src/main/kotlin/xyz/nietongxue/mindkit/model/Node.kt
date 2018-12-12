package xyz.nietongxue.mindkit.model

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.JsonValue
import com.beust.klaxon.lookup

data class Node(val id: String, val title: String, val labels: List<String>, val note: String?, val extensions:JsonArray<JsonValue>?, val children: ArrayList<Node>) {
    //TODO 支持marker
    //TODO marker可能需要抽象，非xmind的source对应为啥？
    //TODO marker是提示应用app的东西之一

    //TODO 支持extensions，html table 需要。
    companion object {
        fun fromJson(json: JsonObject): Node {
            val children: JsonArray<JsonObject> = (json["children"] as JsonObject?)?.array("attached") ?: JsonArray()
            return Node(
                    json["id"] as String,
                    json["title"] as String,
                    (json["labels"] as? JsonArray<*>)?.map { it.toString() } ?: emptyList(),
                    json.lookup<String?>("notes.plain.content")[0],
                    json["extensions"] as? JsonArray<JsonValue>,
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