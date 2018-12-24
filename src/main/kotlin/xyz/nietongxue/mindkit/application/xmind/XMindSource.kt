package xyz.nietongxue.mindkit.application.xmind

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.source.Mounting
import xyz.nietongxue.mindkit.source.Source


class XMindSource(val path: String) : Source {
    override val description: String = "来自于XMind文件 - $path"
    val xMindFile = XMindFile(path)

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
        return listOf(Mounting(tree, listOf(mm.sheets[0].root)))
    }
}