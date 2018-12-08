package xyz.nietongxue.mindkit.application.htmlTable

import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate
import xyz.nietongxue.mindkit.model.Node

class Table(val columns: List<String>, val rows: List<Pair<Node, Map<String, List<Node>>>>) {
    companion object {
        fun <K, V> List<Pair<K, V>>.toMapList(): Map<K, List<V>> {
            return this.groupBy {
                it.first
            }.map {
                it.key to it.value.map {
                    it.second
                }
            }.toMap()
        }

        fun fromNode(root: Node): Table {
            val rowNodes: List<Node> = root.children
            val columnNames: List<String> = rowNodes.flatMap {
                it.children.mapNotNull { it.labels.firstOrNull() }
            }.distinct()
            val re = Table(columnNames, rowNodes.map {
                it to
                        (it.children.filter { it.labels.isNotEmpty() }.map {
                            it.labels.first() to it
                        }).toMapList()
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