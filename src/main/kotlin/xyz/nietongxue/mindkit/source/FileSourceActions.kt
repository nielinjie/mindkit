package xyz.nietongxue.mindkit.source

import javafx.scene.Parent
import javafx.scene.layout.VBox
import tornadofx.label
import xyz.nietongxue.mindkit.actions.Action
import xyz.nietongxue.mindkit.actions.ActionDescriptor
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.util.Priority
import xyz.nietongxue.mindkit.util.defaultPadding
import java.awt.Desktop

@Priority(0)
object FileSourceActions:ActionDescriptor{
    override fun actions(node: Node): List<Action> {

        val oa: Action? =
                (node.source as? Openable)?.let {
                    (object : Action {
                        override fun view(node: Node): Parent? = VBox().apply {
                            defaultPadding()
                            label(node.title + " - 节点的来源文件已打开。")
                        }

                        override val brief: String = "打开"
                        override val description: String = "打开节点的来源文件 - "+ it.file.name
                        override fun action(node: Node) {
                            Desktop.getDesktop().open(it.file)
                        }
                    })
                }
        val ofa: Action? =
                (node as? FileNode)?.let {
                    (object : Action {
                        override fun view(node: Node): Parent? = VBox().apply {
                            defaultPadding()
                            label(node.title + " - 节点文件已打开。")
                        }

                        override val brief: String = "打开"
                        override val description: String = "打开节点文件 - " + it.file.name
                        override fun action(node: Node) {
                            Desktop.getDesktop().open(it.file)
                        }
                    })
                }

        return listOfNotNull(oa,ofa)
    }

}