package xyz.nietongxue.mindkit.actions

import javafx.scene.Parent
import javafx.scene.layout.VBox
import tornadofx.label
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.util.Priority
import xyz.nietongxue.mindkit.util.defaultPadding

@Priority(-1)
object GeneralActions : ActionDescriptor {
    override fun actions(node: Node): List<Action> {
        return listOf(object : Action {
            override fun view(node: Node): Parent? = VBox().apply{
                defaultPadding()
                label(node.title +" - 节点的来源已被加入收藏。")
            }
            override val brief: String = "收藏"
            override val description: String = "收藏节点的来源"
            override fun action(node: Node) {
                //TODO 实现收藏
                println("shocang")
            }
        })
    }

}