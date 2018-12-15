package xyz.nietongxue.mindkit.properties

import javafx.beans.property.SimpleObjectProperty
import tornadofx.Fieldset
import tornadofx.field
import tornadofx.label
import tornadofx.stringBinding
import xyz.nietongxue.mindkit.model.Node

object XMindProperties : Properties {
    override fun fieldSet(nodeP: SimpleObjectProperty<Node>): List<Fieldset> {
        //TODO 根据node的类型或者source判断是否需要这部分properties
        //
        val re = Fieldset("XMind信息")
        with(re){
            field ("Title"){
                label(nodeP.stringBinding{it?.title})
            }
        }
        return listOf(
                re
        )

    }
}