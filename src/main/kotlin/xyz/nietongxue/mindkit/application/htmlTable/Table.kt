package xyz.nietongxue.mindkit.application.htmlTable

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon
import com.beust.klaxon.PathMatcher
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import xyz.nietongxue.mindkit.model.XNode
import xyz.nietongxue.mindkit.util.toHtml
import java.util.regex.Pattern

class Table(val columns: List<String>, val rows: List<Pair<XNode, Map<String, List<XNode>>>>) {
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

        fun fromNode(root: XNode): Table {
            fun orders(json: JsonArray<JsonValue>): List<String> {
                var re = emptyList<String>()
                val pathMatcher = object : PathMatcher {
                    override fun pathMatches(path: String): Boolean {
                        return Pattern.matches(".*content.*content.*content.*", path)
                    }

                    override fun onMatch(path: String, value: Any) {
//                        println("Adding $path = $value")
                        re += value.toString()
                    }
                }

                Klaxon()
                        .pathMatcher(pathMatcher)
                        .parseJsonArray(json.toJsonString().reader())
                return re
            }

            val columnOrder: List<String>? = root.extensions?.let { orders(it) }
            val rowXNodes: List<XNode> = root.children.filterIsInstance<XNode>()

            val columnNames: List<String> = columnOrder ?: rowXNodes.flatMap {
                it.children.filterIsInstance<XNode>().mapNotNull { it.labels.firstOrNull() }
            }.distinct()
            return Table(columnNames, rowXNodes.map {
                it to
                        (it.children.filterIsInstance<XNode>().filter { it.labels.isNotEmpty() }.map {
                            it.labels.first() to it
                        }).toMapList()
            })

        }
    }


    fun toHTML(): String {
        return buildString {
            appendHTML().table {
                thead {
                    tr {
                        th {}
                        this@Table.columns.forEach {
                            th {
                                text(it)
                            }
                        }
                    }
                }
                tbody {
                    this@Table.rows.forEach { row ->
                        tr {
                            td {
                                //unsafe { + row.first.toHtml() }
                                //TODO 这里可能需要一种不带children的toHTML，而不是直接title
                                +row.first.title
                            }
                            this@Table.columns.forEach {
                                td {
                                    if (row.second[it]?.size ?: 0 > 1)
                                        ul {
                                            row.second[it]?.forEach { node ->
                                                li {
                                                    unsafe {
                                                        +node.toHtml()
                                                    }
                                                }
                                            }
                                        }
                                    else
                                        row.second[it]?.forEach { node ->
                                            p {
                                                unsafe {
                                                    +node.toHtml()
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
    }
}