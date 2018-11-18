package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleStringProperty
import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate
import xyz.nietongxue.mindkit.model.Node
import tornadofx.getValue
import tornadofx.setValue

class AppModel {
    val treeModel = TreeModel()
    val resultStringProperty = SimpleStringProperty()
    var resultString by resultStringProperty


    val processorStringProperty = SimpleStringProperty()
    var processorString by processorStringProperty


    var processor: Processor = Processor.nonProcessor

    init {
        val templateString =  AppModel::class.java.getResource("/marpSlide.twig").readText()

        processor = TemplateProcessor(templateString)
        processorString = templateString
    }


    fun process(node: Node) {
        resultString = "running..."
        resultString = this.processor!!.process(node)

    }
}

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

class TemplateProcessor(val templateString: String) : Processor {
    override fun process(node: Node): String {

        val template = JtwigTemplate.inlineTemplate(templateString)
        //.classpathTemplate(templateString)
        val model = JtwigModel.newModel().with("node", node)
        return template.render(model)
    }
}