package xyz.nietongxue.mindkit.application.htmlTable

import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.util.toHtml

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
            return Table(columnNames, rowNodes.map {
                it to
                        (it.children.filter { it.labels.isNotEmpty() }.map {
                            it.labels.first() to it
                        }).toMapList()
            })

        }
    }


    fun toHTML():String{
        return buildString{
            appendHTML().table {
                thead {
                    tr{
                        th{}
                        this@Table.columns.forEach {
                            th{
                                text(it)
                            }
                        }
                    }
                }
                tbody {
                    this@Table.rows.forEach { row->
                        tr{
                            td{
                                //unsafe { + row.first.toHtml() }
                                //TODO 这里可能需要一种不带children的toHTML，而不是直接title
                               + row.first.title
                            }
                            this@Table.columns.forEach {
                                td{
                                    row.second[it]?.forEach{ node->
                                        unsafe {
                                            + node.toHtml()
                                        }
                                        br()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}