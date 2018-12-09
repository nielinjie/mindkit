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

interface Source {
    fun wantAppendTo(parent:Node):List<Node>
    companion object {
        fun append(root:Node):List<Node>{
            return XMindSource.wantAppendTo(root)
        }
    }
}

object XMindSource : Source {
    override fun wantAppendTo(parent: Node): List<Node> {
        val xMindFile = XMindFile("./ppt.xmind")

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
        return listOf(mm.sheets[0].root)
    }

}