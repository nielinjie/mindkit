package xyz.nietongxue.mindkit.util

import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate
import xyz.nietongxue.mindkit.model.Node


class TemplateFunction(val templateString: String)  {
     fun process(node: Node): String {
        val template = JtwigTemplate.inlineTemplate(templateString)
        val model = JtwigModel.newModel().with("node", node)
        return template.render(model)
    }
}