package xyz.nietongxue.mindkit.application.htmlTable

import javafx.scene.Parent
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import tornadofx.cssclass
import xyz.nietongxue.mindkit.actions.Action
import xyz.nietongxue.mindkit.actions.ActionDescriptor
import xyz.nietongxue.mindkit.application.xmind.XNode
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.util.Priority
import xyz.nietongxue.mindkit.util.toHtml

@Priority(100)
object HtmlAction : ActionDescriptor {
    override fun actions(node: Node): List<Action> {
        return listOf(
                object : Action {
                    val con = TableAppController()
                    override val brief: String = "HTML"
                    override val description: String = "显示为HTML"

                    override fun action(node: Node) {
                        val h = buildString {
                            appendHTML().html {
                                body {
                                    classes = setOf("typo")
                                    unsafe {
                                        +node.toHtml()
                                    }

                                }
                            }
                        }
                        con.resultText = h
                    }

                    override fun view(node: Node): Parent? {
                        return con.view.root
                    }

                }
        )
    }

}

@Priority(100)
object HtmlTableAction : ActionDescriptor {
    override fun actions(node: Node): List<Action> {
        return if (node is XNode)
            listOf(
                    object : Action {
                        val con = TableAppController()
                        override val brief: String = "HTML Table"
                        override val description: String = "显示为HTML表格（Table）"

                        override fun action(node: Node) {

                            val h = buildString {
                                appendHTML().html {
                                    body {
                                        classes = setOf("typo")
                                        unsafe {
                                            + Table.fromNode(node as XNode).toHTML()
                                        }

                                    }
                                }
                            }
                            con.resultText = h
                        }

                        override fun view(node: Node): Parent? {
                            return con.view.root
                        }

                    }
            ) else
            emptyList()
    }

}