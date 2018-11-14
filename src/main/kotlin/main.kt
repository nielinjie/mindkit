import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import xyz.nietongxue.mindkit.io.XMindFile
import xyz.nietongxue.mindkit.model.MindMap
import xyz.nietongxue.mindkit.view.ViewNode

fun main(args: Array<String>){
    val xMindFile= XMindFile("./ppt.xmind")
    val json = Parser().parse(xMindFile.content()) as JsonArray<JsonObject>
    val mm = MindMap.fromJson(json!!)
    val view = ViewNode.fromNode(mm.sheets[0].root)
    view.pretty()
}