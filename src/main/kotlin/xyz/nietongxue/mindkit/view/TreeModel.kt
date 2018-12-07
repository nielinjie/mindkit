package xyz.nietongxue.mindkit.view

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import xyz.nietongxue.mindkit.io.FileWatcher
import xyz.nietongxue.mindkit.io.XMindFile
import xyz.nietongxue.mindkit.model.MindMap
import java.io.File

class TreeModel {
    var root:ViewNode = ViewNode.emptyRoot

    fun addXmind() {
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
        root.addChildren(listOf(mm.sheets[0].root))


    }


}