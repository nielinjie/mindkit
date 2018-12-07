package xyz.nietongxue.mindkit.application.htmlTable

import javafx.beans.property.SimpleStringProperty
import javafx.scene.Parent
import javafx.scene.layout.VBox
import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate
import tornadofx.*
import xyz.nietongxue.mindkit.application.AppDescriptor
import xyz.nietongxue.mindkit.application.Controller
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.Processor

object HtmlTable : AppDescriptor {
    //TODO xmind  里面的table，生成html（或者markdown？）table，比如可以copy到conf
    // - 生成html可以（至少可以以safari打开，然后copy到conf editor）
    // 后续看看是否可以展示在fx里，然后copy到clipboard。


    override val name: String = "Html Table"
    override val description: String = "生成HTML Table，用于Copy到Conf"
    override val providedProcessors: List<Processor> = listOf(object : Processor {
        override fun process(node: Node): String {
            val table = Table.fromNode(node)
//            println(table.toHTML())
            return table.toHTML()
        }

        override val brief: String = "Html Table"
        override val description: String = "生成HTML Table，用于Copy到Conf"
    })
    override val controller: Controller = TableController()
}

class TableController : Controller {
    val resultTextP = SimpleStringProperty()
    var resultText by resultTextP
    override fun process(node: Node) {
        resultText = (this.processor?.process(node))
    }

    override var processor: Processor? = null

    override val view: View = object : View() {
        override val root: Parent = VBox()

        init {
            with(root) {
                text(resultTextP)
                webview {
                    dynamicContent(resultTextP) {
                        engine.loadContent(it)
                    }
                }
            }
        }

    }
}

class Table(val columns: List<String>, val rows: List<Pair<Node, Map<String, Node>>>) {
    companion object {
        fun fromNode(root: Node): Table {
            val rowNodes: List<Node> = root.children
            val columnNames: List<String> = rowNodes.flatMap {
                it.children.mapNotNull { it.labels.firstOrNull() }
            }.distinct()
            val re = Table(columnNames, rowNodes.map {
                it to
                        it.children.filter { it.labels.isNotEmpty() }.map {
                            it.labels.first() to it
                        }.toMap()
            })
            return re

        }
    }

    fun toHTML(): String {
        val templateString = Table::class.java.getResource("/htmlTable.twig").readText()
        val template = JtwigTemplate.inlineTemplate(templateString)
        val model = JtwigModel.newModel().with("table", this)
        return template.render(model)
    }
}