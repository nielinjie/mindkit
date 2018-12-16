package xyz.nietongxue.mindkit.source

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import xyz.nietongxue.mindkit.io.XMindFile
import xyz.nietongxue.mindkit.model.MindMap
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.XNode

//TODO 不同的source跟不同的app有没有什么关系。
//TODO 应该没有，app可能会统一到action机制。
class XMindSource(val path: String) : Source {
    override val description: String = "来自于XMind文件 - $path"

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
        val mm = MindMap.fromJson(json,this)
        return listOf(Mounting(tree, listOf(mm.sheets[0].root)))
    }
}