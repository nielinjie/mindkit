package xyz.nietongxue.mindkit.actions

import javafx.scene.Parent
import javafx.scene.layout.VBox
import tornadofx.label
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.util.defaultPadding
import xyz.nietongxue.mindkit.util.scanForInstance


interface ActionDescriptor {
    fun actions(node: Node): List<Action>

    companion object {
        private val scanForInstance = scanForInstance(ActionDescriptor::class)
        fun actions(node: Node): List<Action> {
            return scanForInstance.flatMap {
                it.actions(node)
            }
        }

        fun default(): ActionDescriptor {
            return scanForInstance.first()
        }
    }
}

//NOTE Action 机制，提示在此节点，或者此节点"附近"可以做点啥。
//NOTE Action是对一个节点的显式操作，而不是选中节点即发生的操作，那个叫app
interface Action {
    val brief: String
    val description: String
    fun action(node: Node)
    fun view(node: Node): Parent?
    fun basicResultStringView(result:String):Parent =
        VBox().apply {
            defaultPadding()
            label(result)

    }
}

