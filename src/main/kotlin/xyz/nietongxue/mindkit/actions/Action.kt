package xyz.nietongxue.mindkit.actions

import javafx.scene.Parent
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.util.scanForInstance



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

//TODO  Action 机制，提示在此节点，或者此节点"附近"可以做点啥。
interface Action {
    val brief: String
    val description: String
    fun action(node:Node)
    fun view(node:Node): Parent?
}

