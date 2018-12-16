package xyz.nietongxue.mindkit.model

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.JsonValue
import com.beust.klaxon.lookup



interface Node{
    val id: String
    val title: String
    val children: ArrayList<Node>

}

data class XNode(override val id: String,
                 override val title: String,
                 val labels: List<String>,
                 val note: String?,
                 val extensions:JsonArray<JsonValue>?,
                 //TODO 如何通过类型系统限制xnode的children都是xnode？
                 override val children: ArrayList<Node>) :Node{
    //TODO 支持marker
    //TODO marker可能需要抽象，非xmind的source对应为啥？
    //TODO marker是提示应用app的东西之一

    companion object {
        fun fromJson(json: JsonObject): XNode {
            val children: JsonArray<JsonObject> = (json["children"] as JsonObject?)?.array("attached") ?: JsonArray()
            return XNode(
                    json["id"] as String,
                    json["title"] as String,
                    (json["labels"] as? JsonArray<*>)?.map { it.toString() } ?: emptyList(),
                    json.lookup<String?>("notes.plain.content")[0],
                    json["extensions"] as? JsonArray<JsonValue>,
                    ArrayList(children.map {
                        XNode.fromJson(it)
                    }.toList())
            )
        }
    }
}

data class Sheet(val root: XNode) {
    companion object {
        fun fromJson(json: JsonObject): Sheet {
            return Sheet(XNode.fromJson(json["rootTopic"] as JsonObject))
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