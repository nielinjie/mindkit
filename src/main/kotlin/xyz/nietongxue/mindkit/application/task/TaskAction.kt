package xyz.nietongxue.mindkit.application.task

import javafx.scene.Parent
import xyz.nietongxue.mindkit.actions.Action
import xyz.nietongxue.mindkit.actions.ActionDescriptor
import xyz.nietongxue.mindkit.model.Markers
import xyz.nietongxue.mindkit.model.Node

class TaskAction : ActionDescriptor {
    override fun actions(node: Node): List<Action> {
        val nodes = Markers.findByFamily(node, "task")
        if (nodes.isNotEmpty()) {
            return listOf(
                    object : Action {
                        override val brief: String = "列表"
                        override val description: String = "发现代办任务"
                        override fun action(node: Node) {
                        }

                        override fun view(node: Node): Parent? {
                            return basicResultStringView(nodes.joinToString("\n") { it.title })
                        }

                    }
            )
        } else return emptyList()
    }
}