package xyz.nietongxue.mindkit.actions

import javafx.beans.property.SimpleObjectProperty
import tornadofx.*
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.properties.Properties
import xyz.nietongxue.mindkit.util.Priority


@Priority(value = -100)
object ActionProperties : Properties{
    override fun fieldSet(nodeP: SimpleObjectProperty<Node>): List<Fieldset> {
        val actions:List<Action> = ActionDescriptor.actions(nodeP.value)

        val re =
                Fieldset("Action信息")
        with(re) {
            actions.forEach {
                field(it.description) {
                    hyperlink (it.brief){
                        action{
                            it.action()
                        }
                    }
                }
            }

        }
        return listOf(re)
    }

}