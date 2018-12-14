package xyz.nietongxue.mindkit.model

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import xyz.nietongxue.mindkit.io.XMindFile
import java.io.File

//TODO 多文件
//TODO 一个目录作为home（repository）

//TODO 多类型

/*TODO filter？或者合并在类型里面 filter貌似应该是app级别的，有些app关注这些节点，有些app关注哪些节点？
   filter跟processor如何配合？apply一个app将应用多个filter 和多个 function？
 */

data class Mounting(val where: Node, val what: List<Node>)
interface Source {
    fun mount(tree: Node, mountPoint: Node = tree): List<Mounting>
//    companion object {
//        fun append(root:Node):List<Node>{
//            //TODO 扫描classpath
//            //TODO 没有弄清楚怎么个逐步挂上去，现在只支持挂到root上。

//            val all = listOf(XMindSource)
//            return all.flatMap { it.mount(root).what }
//        }
//    }
}

/**
@flat 如果是true，表示不需要保留文件层次结构的node。
 */
class FolderSource(val path: String, val flat: Boolean = true) : Source {

    init {

    }

    override fun mount(tree: Node, mountPoint: Node): List<Mounting> {
        //TODO 只实现了flat是true
        assert(flat)
        return File(path).walk().filter {
            //TODO 实现根据文件内容选择不同的source
             it.isFile && it.extension == "xmind"
        }.map {
//            println(it.path)
            XMindSource(it.path)
        }.toList().flatMap {
            it.mount(tree, mountPoint)
        }
    }

}

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
