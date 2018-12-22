package xyz.nietongxue.mindkit.application

import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate
import xyz.nietongxue.mindkit.application.xmind.Node

interface Function {
    //TODO processor是否应该还返回string，或者processor是否已经没有什么用了？
    //TODO 彻底清理掉function
    fun process(node: Node): String
    val brief: String
    val description: String

}



abstract class TemplateFunction(val templateString: String) : Function {
    override fun process(node: Node): String {
        val template = JtwigTemplate.inlineTemplate(templateString)
        val model = JtwigModel.newModel().with("node", node)
        return template.render(model)
    }
}