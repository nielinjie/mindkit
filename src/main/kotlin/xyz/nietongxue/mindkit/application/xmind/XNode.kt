package xyz.nietongxue.mindkit.application.xmind

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.JsonValue
import com.beust.klaxon.lookup
import sun.jvm.hotspot.oops.Mark
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.source.Source



data class XNode(override val id: String,
                 override val title: String,
                 val labels: List<String>,
                 val note: String?,
                 val markers:List<Marker>,
                 val extensions:JsonArray<JsonValue>?,
                 //TODO 如何通过类型系统限制xnode的children都是xnode？
                 override val children: ArrayList<Node>, override val source: Source) : Node {

    //TODO marker可能需要抽象，非xmind的source对应为啥？
    //TODO marker是提示应用app的东西之一

    //TODO 图片（或者包括其他附件？）

    //TODO 独立节点
    //TODO 多sheet

    companion object {
        fun fromJson(json: JsonObject,source: Source): XNode {
            val children: JsonArray<JsonObject> = (json["children"] as JsonObject?)?.array("attached") ?: JsonArray()
            return XNode(
                    json["id"] as String,
                    json["title"] as String,
                    (json["labels"] as? JsonArray<*>)?.map { it.toString() } ?: emptyList(),
                    json.lookup<String?>("notes.plain.content")[0],
                    json.lookup<String?>("markers.markerId").filterNotNull() .map{Marker(it)},
                    json["extensions"] as? JsonArray<JsonValue>,
                    ArrayList(children.map {
                        fromJson(it, source)
                    }.toList())
                    , source
            )
        }
    }
}

data class Sheet(val root: XNode) {
    companion object {
        fun fromJson(json: JsonObject,source: Source): Sheet {
            return Sheet(XNode.fromJson(json["rootTopic"] as JsonObject, source))
        }
    }
}

data class MindMap(val sheets: List<Sheet>) {
    companion object {
        fun fromJson(json: JsonArray<JsonObject>,source: Source): MindMap {
            return MindMap(listOf(Sheet.fromJson(json.first(), source)))
        }
    }
}
data class Marker(val id:String)