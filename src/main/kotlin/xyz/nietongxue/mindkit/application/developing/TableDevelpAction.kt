package xyz.nietongxue.mindkit.application.developing

import com.beust.klaxon.lookup
import javafx.scene.Parent
import xyz.nietongxue.mindkit.actions.Action
import xyz.nietongxue.mindkit.actions.ActionDescriptor
import xyz.nietongxue.mindkit.application.xmind.XNode
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.util.Disabled

@Disabled
class TableDevelAction : ActionDescriptor {
    override fun actions(node: Node): List<Action> {
        return listOf(object : Action {
            override val brief: String = "打印"
            override val description: String = "打印extends"

            override fun action(node: Node) {
            }

            override fun view(node: Node): Parent? = basicResultStringView(
                    ((node as? XNode)?.let {
                        it.extensions?.toJsonString(prettyPrint = true) +"\n"+
                                (it.extensions?.lookup<String?>("provider")?.get(0) == "org.xmind.ui.spreadsheet" ).toString() }
                            )?:"no extensions"
            )

        })
    }
}