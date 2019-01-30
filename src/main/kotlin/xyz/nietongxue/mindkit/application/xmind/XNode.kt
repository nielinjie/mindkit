package xyz.nietongxue.mindkit.application.xmind

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.JsonValue
import com.beust.klaxon.lookup
import xyz.nietongxue.mindkit.model.Marker
import xyz.nietongxue.mindkit.model.Markers
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.source.InternalSource
import xyz.nietongxue.mindkit.model.source.Source
import java.util.*


data class XNode(override val id: String,
                 override val title: String,
                 val labels: List<String>,
                 val note: String?,
                 //TODO Marker的识别应该是source/node的职责还是application的职责？
                 override val markers: List<Marker>,
                 val originalMarkers:List<String>,

                 val image: Image?,
                 val extensions: JsonArray<JsonValue>?,
                 override val children: ArrayList<Node>,
                 override val source: Source) : Node {


    companion object {
        fun fromJson(json: JsonObject, source: Source): XNode {
            /*

             "image": {
              "src": "xap:resources/68e657cdcfed6947e80b768a15114c77027b94220bf4a2026cd05797924d5976.png",
              "type": "image"
            }

             */
            val children: JsonArray<JsonObject> = (json["children"] as JsonObject?)?.array("attached") ?: JsonArray()
            val children2: JsonArray<JsonObject> = (json["children"] as JsonObject?)?.array("detached") ?: JsonArray()

            return try {
                XNode(
                        json["id"] as String,
                        json["title"] as? String ?: "",
                        (json["labels"] as? JsonArray<*>)?.map { it.toString() } ?: emptyList(),
                        json.lookup<String?>("notes.plain.content")[0],
                        json.lookup<String?>("markers.markerId").filterNotNull().map { XmindMarker(it).toGeneral() }.flatten().distinct(),
                        json.lookup<String?>("markers.markerId").filterNotNull(),
                        (json["image"]  as? JsonObject)?.let {
                            Image(it["src"] as String, it["type"] as? String)
                        },
                        json["extensions"] as? JsonArray<JsonValue>,
                        ArrayList((children.toList().plus(children2)).map {
                            fromJson(it, source)
                        }.toList())
                        , source
                )
            } catch (e: Exception) {
                XNode(UUID.randomUUID().toString(), "error", emptyList(), null, emptyList(), emptyList(),null, null, arrayListOf(),InternalSource)
            }
        }
    }
}

data class Sheet(val root: XNode) {
    companion object {
        fun fromJson(json: JsonObject, source: Source): Sheet {
            return Sheet(XNode.fromJson(json["rootTopic"] as JsonObject, source))
        }
    }
}

data class MindMap(val sheets: List<Sheet>) {
    companion object {
        fun fromJson(json: JsonArray<JsonObject>, source: Source): MindMap {
            return MindMap(json.map { Sheet.fromJson(it, source) })
        }

    }
}

data class XmindMarker(val id: String) {
    fun toGeneral(): List<Marker> {
        val nameMap: Map<String, String> = mapOf(
                "c_symbol_hourglass" to "wait",
                "priority-0" to "p0",
                "priority-1" to "p1",
                "priority-2" to "p2",
                "priority-3" to "p3",
                "priority-4" to "p4",
                "priority-5" to "p5",
                "symbol-attention" to "attention",
                "symbol-question" to "question",
                "task-oct" to "task 12.5p",
                "task-3oct" to "task 37.5p",
                "task-5oct" to "task 62.5p",
                "task-7oct" to "task 87.5p",
                "task-done" to "task 100p",
                "task-half" to "task 50p",
                "task-start" to "task 0p"

//TODO 增加更多的marker。注意id不等于icon的文件名
                        //task-5oct 没有，oct是不是八分之的意思？
        //写个程序，把所有的markerId全部列出来。
        //来一个debug用的的action
                )
        return listOfNotNull(nameMap[id]?.split(" ")?.let {names:List<String> ->
            names.mapNotNull {Markers.byName(it) }
        }).flatten()
    }
}

data class Image(val src: String, val type: String?)