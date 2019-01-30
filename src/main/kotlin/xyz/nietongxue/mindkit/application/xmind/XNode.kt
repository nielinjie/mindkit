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
                 override val markers:  MutableList<Marker>,
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
                        mutableListOf(),
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
                XNode(UUID.randomUUID().toString(), "error", emptyList(), null, mutableListOf(), emptyList(),null, null, arrayListOf(),InternalSource)
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



data class Image(val src: String, val type: String?)