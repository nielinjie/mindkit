package xyz.nietongxue.mindkit.model

import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate

interface Processor{
    fun process(node:Node):String
    companion object {
        val nonProcessor = object: Processor{
            override fun process(node: Node): String {
                return ""
            }
        }
    }
}
class TemplateProcessor(templateString:String):Processor{
    override fun process(node:Node):String{
        val template = JtwigTemplate.classpathTemplate("/marpSlide.twig")
        val model = JtwigModel.newModel().with("node", node)
        return template.render(model)
    }
}