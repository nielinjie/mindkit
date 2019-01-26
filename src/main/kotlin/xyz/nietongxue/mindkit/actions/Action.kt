package xyz.nietongxue.mindkit.actions

import javafx.scene.Parent
import xyz.nietongxue.mindkit.model.Node
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

//NOTE  Action 机制，提示在此节点，或者此节点"附近"可以做点啥。
interface Action {
    val brief: String
    val description: String
    fun action(node: Node)
    fun view(node: Node): Parent?
}

