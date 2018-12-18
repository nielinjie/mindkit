package xyz.nietongxue.mindkit.actions

import javafx.beans.property.SimpleObjectProperty
import tornadofx.*
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.properties.Properties



object ActionProperties : Properties{
    override fun fieldSet(nodeP: SimpleObjectProperty<Node>): List<Fieldset> {
        val actions:List<Action> = emptyList()//

        val re =
                Fieldset("Action信息")
        with(re) {
            actions.forEach {
                field(it.description) {
                    button(it.brief){
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