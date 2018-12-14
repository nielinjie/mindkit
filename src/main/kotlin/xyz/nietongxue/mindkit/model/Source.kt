package xyz.nietongxue.mindkit.model

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import xyz.nietongxue.mindkit.io.XMindFile

//TODO 多文件
//TODO 一个目录作为home（repository）

//TODO 多类型

/*TODO filter？或者合并在类型里面 filter貌似应该是app级别的，有些app关注这些节点，有些app关注哪些节点？
   filter跟processor如何配合？apply一个app将应用多个filter 和多个 function？
 */

data class Mounting(val where: Node, val what: List<Node>)
interface Source {
    fun mount(tree: Node): Mounting
//    companion object {
//        fun append(root:Node):List<Node>{
//            //TODO 扫描classpath
//            //TODO 没有弄清楚怎么个逐步挂上去，现在只支持挂到root上。

//            val all = listOf(XMindSource)
//            return all.flatMap { it.mount(root).what }
//        }
//    }
}


//TODO 不同的source跟不同的app有没有什么关系。
class XMindSource(val path: String) : Source {
    override fun mount(tree: Node): Mounting {
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
        val json = Parser().parse(xMindFile.content()) as JsonArray<JsonObject>
        val mm = MindMap.fromJson(json)
        return Mounting(tree, listOf(mm.sheets[0].root))
    }
}
