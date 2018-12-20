package xyz.nietongxue.mindkit.actions

import javafx.scene.Parent
import javafx.scene.layout.VBox
import tornadofx.label
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.util.Priority
import xyz.nietongxue.mindkit.util.scanForInstance

//TODO 可以替代function？
//TODO 以tabpane组织，从properties的tab触发，每个action的结果放到一个单独的tab。可以关闭。


interface ActionDescriptor {
    fun actions(node: Node): List<Action>

    companion object {
        fun actions(node: Node): List<Action> {
            return scanForInstance(ActionDescriptor::class)
                    .flatMap {
                        it.actions(node)
                    }
        }
    }
}

interface Action {
    val brief: String
    val description: String
    //TODO 定义运行些啥
    fun action(node:Node)
    fun view(node:Node): Parent?
}

@Priority(-1)
object GeneralActions : ActionDescriptor {
    override fun actions(node: Node): List<Action> {
        return listOf(object : Action {
            override fun view(node: Node): Parent? = VBox().apply{
                label(node.title)
            }
            override val brief: String = "收藏"
            override val description: String = "收藏节点的来源"
            override fun action(node:Node) {
                println("shocang")
            }
        })
    }

}