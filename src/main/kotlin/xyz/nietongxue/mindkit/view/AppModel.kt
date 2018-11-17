package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleStringProperty
import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate
import xyz.nietongxue.mindkit.model.Node
import tornadofx.getValue
import tornadofx.setValue

class AppModel {
    val treeModel = TreeModel()
    val generatedStringProperty = SimpleStringProperty("kaka")
    var generatedString by generatedStringProperty
    fun generate(node: Node) {
        generatedString="running..."
        val template = JtwigTemplate.classpathTemplate("/hello.twig")
        val model = JtwigModel.newModel().with("node", node)
        generatedString = template.render(model)

    }
}