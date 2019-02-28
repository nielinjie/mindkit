package xyz.nietongxue.mindkit.application.xmind

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.source.Mounting
import xyz.nietongxue.mindkit.model.source.Source
import xyz.nietongxue.mindkit.view.ViewNode
import java.io.InputStream
import java.util.zip.ZipFile

class XMindFile(val path: String) {
    fun content(): InputStream? {
        val zipFile = ZipFile(path)
        val entry = zipFile.getEntry("content.json")
        return entry?.let {
            zipFile.getInputStream(it)
        }
    }

    fun resource(resourceSrc: String): InputStream? {
        require(resourceSrc.startsWith("xap:"))
        val resourcePath = resourceSrc.drop(4)
        val zipFile = ZipFile(path)
        val entry = zipFile.getEntry(resourcePath)
        return entry?.let {
            zipFile.getInputStream(it)
        }
    }
}


fun main(args: Array<String>) {
    val xMindFile = XMindFile("/Users/nielinjie/Desktop/ppt.xmind")
    val json = Parser().parse(xMindFile.content()!!) as JsonArray<JsonObject>
    println(json.toJsonString(true))
    val mm = MindMap.fromJson(json, object : Source {
        override val description: String
            get() = throw NotImplementedError("not implemented") //To change initializer of created properties use File | Settings | File Templates.

        override fun mount(tree: Node, mountPoint: Node): List<Mounting> {
            throw NotImplementedError("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    })
    val view = ViewNode.fromNode(mm.sheets[0].root)
}