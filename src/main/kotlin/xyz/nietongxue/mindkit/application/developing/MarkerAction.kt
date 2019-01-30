package xyz.nietongxue.mindkit.application.developing

import javafx.scene.Parent
import xyz.nietongxue.mindkit.actions.Action
import xyz.nietongxue.mindkit.actions.ActionDescriptor
import xyz.nietongxue.mindkit.application.xmind.XNode
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.util.Disabled
import xyz.nietongxue.mindkit.util.Priority

@Disabled
class MarkerAction : ActionDescriptor {
    override fun actions(node: Node): List<Action> {
        return listOf(object : Action {
            override val brief: String = "打印"
            override val description: String = "打印所有可能的MarkerId"

            override fun action(node: Node) {
            }

            override fun view(node: Node): Parent? = basicResultStringView(
                    node.collect { (it as? XNode)?.originalMarkers ?: emptyList() }
                            .flatten().distinct().sorted().joinToString ( "\n" ).also { println(it) }
            )

        })
    }
}