package xyz.nietongxue.mindkit.application.htmlTable

import javafx.scene.Parent
import xyz.nietongxue.mindkit.actions.Action
import xyz.nietongxue.mindkit.actions.ActionDescriptor
import xyz.nietongxue.mindkit.application.xmind.XNode
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.util.toHtml


object HtmlAction:ActionDescriptor{
    override fun actions(node: Node): List<Action> {
        return listOf(
                object:Action{
                    val con = TableAppController()
                    override val brief: String = "HTML"
                    override val description: String = "显示为HTML"

                    override fun action(node: Node) {
                        con.resultText = node.toHtml()
                    }

                    override fun view(node: Node): Parent? {
                        return con.view.root
                    }

                }
        )
    }

}


object HtmlTableAction:ActionDescriptor{
    override fun actions(node: Node): List<Action> {
        return  if(node is XNode)
            listOf(
                object:Action{
                    val con = TableAppController()
                    override val brief: String = "HTML Table"
                    override val description: String = "显示为HTML表格（Table）"

                    override fun action(node: Node) {
                        con.resultText = Table.fromNode(node as XNode).toHTML()
                    }

                    override fun view(node: Node): Parent? {
                        return con.view.root
                    }

                }
        )else
            emptyList()
    }

}