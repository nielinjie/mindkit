package xyz.nietongxue.mindkit.application.xmind

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.JsonValue
import com.beust.klaxon.lookup
import xyz.nietongxue.mindkit.model.Marker
import xyz.nietongxue.mindkit.model.Markers
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.source.InternalSource
import xyz.nietongxue.mindkit.source.Source
import java.io.InputStream
import java.util.*


data class XNode(override val id: String,
                 override val title: String,
                 val labels: List<String>,
                 val note: String?,
                 override val markers: List<Marker>,
                 val image: Image?,
                 val extensions: JsonArray<JsonValue>?,
        //TODO 如何通过类型系统限制xnode的children都是xnode？
                 override val children: ArrayList<Node>,
                 override val source: Source) : Node {


    //TODO 多sheet

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
                        json["title"] as? String ?:"",
                        (json["labels"] as? JsonArray<*>)?.map { it.toString() } ?: emptyList(),
                        json.lookup<String?>("notes.plain.content")[0],
                        json.lookup<String?>("markers.markerId").filterNotNull().map { XmindMarker(it).toGeneral() }.flatten().distinct(),
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
                XNode(UUID.randomUUID().toString(), "error", emptyList(), null, emptyList(), null, null, arrayListOf(), InternalSource)
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
            return MindMap(listOf(Sheet.fromJson(json.first(), source)))
        }
    }
}

data class XmindMarker(val id: String) {
//    fun inputStream(): InputStream {
//        return XmindMarker::class.java.getResourceAsStream("/xyz/nietongxue/mindkit/application/xmind/markers/"
//                + this.id.replace("-", "_")
//                + "@24@2x.png")
//                ?: XmindMarker::class.java.getResourceAsStream("/xyz/nietongxue/mindkit/application/xmind/markers/"
//                        + "symbol_wrong"
//                        + "@24@2x.png")
//    }
    fun toGeneral():List<Marker>{
        println(id)
        val nameMap :Map<String,String> = mapOf(
                "priority_1" to "p1"
        )
        return listOfNotNull(nameMap[id]?.let{Markers.byName(it)})
    }
}

data class Image(val src: String, val type: String?)