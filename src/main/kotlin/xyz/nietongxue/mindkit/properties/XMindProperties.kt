package xyz.nietongxue.mindkit.properties

import javafx.beans.property.SimpleObjectProperty
import tornadofx.Fieldset
import tornadofx.field
import tornadofx.label
import tornadofx.stringBinding
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.XNode

object XMindProperties : Properties {
    override fun fieldSet(nodeP: SimpleObjectProperty<Node>): List<Fieldset> {
        return if(nodeP.value is XNode){
            val re = Fieldset("XMind信息")
            with(re){
                field ("Title"){
                    label(nodeP.stringBinding{it?.title})
                }
            }
            listOf(
                    re
            )
        }
        else{
            emptyList()
        }
    }
}