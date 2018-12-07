package xyz.nietongxue.mindkit.model

import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate

interface Processor {
    //TODO processor是否应该还返回string，或者processor是否已经没有什么用了？
    fun process(node: Node): String
    val brief: String
    val description: String

}



abstract class TemplateProcessor(val templateString: String) : Processor {
    override fun process(node: Node): String {
        val template = JtwigTemplate.inlineTemplate(templateString)
        val model = JtwigModel.newModel().with("node", node)
        return template.render(model)
    }
}