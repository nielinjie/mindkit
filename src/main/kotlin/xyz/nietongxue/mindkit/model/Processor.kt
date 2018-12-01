package xyz.nietongxue.mindkit.model

import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate

interface Processor {
    fun process(node: Node): String
    val brief:String
    val description:String

    companion object {
        val nonProcessor = object : Processor {
            override val brief ="None"
            override val description: String
                get() = "什么都不做的一个处理器"
            override fun process(node: Node): String {
                return ""
            }
        }
    }
}

class Processors {
    val all: List<Processor> by lazy {
        listOf(
                Processor.nonProcessor,
                object:TemplateProcessor(Processors::class.java.getResource("/marpSlide.twig").readText()){
                    override val brief: String
                        get() = "Marp Slides 模板"
                    override val description: String
                        get() = this.templateString

                }
        )
    }
}

abstract class TemplateProcessor(val templateString: String) : Processor {
    override fun process(node: Node): String {
        val template = JtwigTemplate.inlineTemplate(templateString)
        val model = JtwigModel.newModel().with("node", node)
        return template.render(model)
    }
}