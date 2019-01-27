import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import xyz.nietongxue.mindkit.application.xmind.MindMap
import xyz.nietongxue.mindkit.application.xmind.XMindFile
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.source.Mounting
import xyz.nietongxue.mindkit.model.source.Source
import xyz.nietongxue.mindkit.view.ViewNode

fun main(args: Array<String>){
    val xMindFile= XMindFile("/Users/nielinjie/Desktop/ppt.xmind")
    val json = Parser().parse(xMindFile.content()!!) as JsonArray<JsonObject>
    println(json.toJsonString(true))
    val mm = MindMap.fromJson(json,object: Source{
        override val description: String
            get() = throw NotImplementedError("not implemented") //To change initializer of created properties use File | Settings | File Templates.

        override fun mount(tree: Node, mountPoint: Node): List<Mounting> {
            throw NotImplementedError("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    })
    val view = ViewNode.fromNode(mm.sheets[0].root)
//
//
//    val template = JtwigTemplate.classpathTemplate("/marpSlide.twig")
//    val model = JtwigModel.newModel().with("var", "World")
//
//    template.render(model, System.out)
}