import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import xyz.nietongxue.mindkit.io.XMindFile
import xyz.nietongxue.mindkit.model.MindMap
import xyz.nietongxue.mindkit.view.ViewNode
import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate



fun main(args: Array<String>){
    val xMindFile= XMindFile("./ppt.xmind")
    val json = Parser().parse(xMindFile.content()) as JsonArray<JsonObject>
    println(json.toJsonString(true))
    val mm = MindMap.fromJson(json)
    val view = ViewNode.fromNode(mm.sheets[0].root)
    view.pretty()


    val template = JtwigTemplate.classpathTemplate("/marpSlide.twig")
    val model = JtwigModel.newModel().with("var", "World")

    template.render(model, System.out)
}