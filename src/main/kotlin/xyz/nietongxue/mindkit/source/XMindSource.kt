package xyz.nietongxue.mindkit.source

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import xyz.nietongxue.mindkit.io.XMindFile
import xyz.nietongxue.mindkit.model.MindMap
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.XNode

//TODO 不同的source跟不同的app有没有什么关系。
class XMindSource(val path: String) : Source {
    override fun mount(tree: Node, mountPoint: Node): List<Mounting> {
        val xMindFile = XMindFile(path)

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
        val mm = MindMap.fromJson(json)
        return listOf(Mounting(tree, listOf(mm.sheets[0].root)))
    }
}