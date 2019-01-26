package xyz.nietongxue.mindkit.application.xmind

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.source.*
import java.io.File



object XmindFileSource : FileSourceDescriptor {
    override fun fileToSource(file: File): List<FileSource> {
        return if (file.isFile && file.extension == "xmind")
            listOf(XMindSource(file.path))
        else emptyList()
    }
}


class XMindSource(path: String) : FileSource, Openable {
    override val description: String = "XMind - $path"
    val xMindFile = XMindFile(path)
    override val file = File(path)
    override fun mount(tree: Node, mountPoint: Node): List<Mounting> {

//        val watcher = FileWatcher(File(".")) { file: File, eventType: String ->
//            println(file.path)
//            println(eventType)
//        }
//        val watcher2 = FileWatcher(File("./ppt.xmind")) { file: File, eventType: String ->
//            print("2-----")
//            println(file.path)
//            println(eventType)
//        }
        val content = xMindFile.content() ?: return emptyList()
        val json = Parser().parse(content) as JsonArray<JsonObject>
        val mm = MindMap.fromJson(json,this)
        return listOf(Mounting(mountPoint) {
            listOf(mm.sheets[0].root)}
        )
    }
}