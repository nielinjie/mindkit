package xyz.nietongxue.mindkit.model

import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate
import xyz.nietongxue.mindkit.application.AppDescriptor
import xyz.nietongxue.mindkit.application.AppDescriptor.Companion.nonApp
import xyz.nietongxue.mindkit.application.marpPPT.MarpPPT

interface Processor {
    fun process(node: Node): String
    val brief: String
    val description: String

}

class Processors {

}

abstract class TemplateProcessor(val templateString: String) : Processor {
    override fun process(node: Node): String {
        val template = JtwigTemplate.inlineTemplate(templateString)
        val model = JtwigModel.newModel().with("node", node)
        return template.render(model)
    }
}