package xyz.nietongxue.mindkit.properties

import com.sun.xml.internal.ws.spi.db.FieldSetter
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Label
import javafx.scene.text.Text
import tornadofx.*
import xyz.nietongxue.mindkit.model.Node

interface Properties{
    fun fieldset(nodeP:SimpleObjectProperty<Node>):List<Fieldset>
    companion object {
        fun pros(nodeP:SimpleObjectProperty<Node>):List<Fieldset> = listOf(XMindProperties()).flatMap {
            it.fieldset(nodeP)
        }
    }
}

class XMindProperties :Properties{
    override fun fieldset(nodeP: SimpleObjectProperty<Node>): List<Fieldset> {
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