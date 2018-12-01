package xyz.nietongxue.mindkit.model

import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate

interface Processor {
    fun process(node: Node): String

    companion object {
        val nonProcessor = object : Processor {
            override fun process(node: Node): String {
                return ""
            }
        }
    }
}

class Processors {
    val all: List<Processor> by lazy {
        listOf(
                TemplateProcessor(Processors::class.java.getResource("/marpSlide.twig").readText())
        )
    }
}

class TemplateProcessor(val templateString: String) : Processor {
    override fun process(node: Node): String {
        val template = JtwigTemplate.inlineTemplate(templateString)
        val model = JtwigModel.newModel().with("node", node)
        return template.render(model)
    }
}