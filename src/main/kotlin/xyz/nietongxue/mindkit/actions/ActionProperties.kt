package xyz.nietongxue.mindkit.actions

import javafx.beans.property.SimpleObjectProperty
import tornadofx.*
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.properties.Properties
import xyz.nietongxue.mindkit.util.Global
import xyz.nietongxue.mindkit.util.Priority


@Priority(value = -100)
object ActionProperties : Properties {
    override fun fieldSet(nodeP: SimpleObjectProperty<Node>): List<Fieldset> {
        val actions: List<Action> = ActionDescriptor.actions(nodeP.value)
        return listOf(Fieldset("Action信息").apply {
            actions.forEach {
                field(it.description) {
                    hyperlink(it.brief) {
                        action {
                            //setup action view
                            it.view(nodeP.value)?.also { view ->
                                //TODO 复用tab ?
                                Global.resultPane?.apply {
                                    children.clear()
                                    add(view)
                                }
                            }
                            //
                            it.action(nodeP.value)
                        }
                    }
                }
            }
        })
    }
}
