package xyz.nietongxue.mindkit.view

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import javafx.collections.ObservableList
import xyz.nietongxue.mindkit.io.XMindFile
import xyz.nietongxue.mindkit.model.MindMap

class TreeModel(){
    var root:ViewNode = ViewNode.emptyRoot

    fun addXmind() {
        val xMindFile = XMindFile("./ppt.xmind")


//        val watcher2 = FileWatcher(File("./ppt.xmind")) { file: File, eventType: String ->
//            print("2-----")
//            println(file.path)
//            println(eventType)
//        }
        val json = Parser().parse(xMindFile.content()) as JsonArray<JsonObject>
        val mm = MindMap.fromJson(json!!)
        root.addChildren(listOf(mm.sheets[0].root))
    }


}