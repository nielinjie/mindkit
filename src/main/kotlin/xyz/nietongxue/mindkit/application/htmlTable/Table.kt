package xyz.nietongxue.mindkit.application.htmlTable

import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate
import xyz.nietongxue.mindkit.model.Node

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