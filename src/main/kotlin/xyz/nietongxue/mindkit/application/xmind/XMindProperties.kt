package xyz.nietongxue.mindkit.application.xmind

import javafx.beans.property.SimpleObjectProperty
import tornadofx.Fieldset
import tornadofx.field
import tornadofx.label
import tornadofx.stringBinding
import xyz.nietongxue.mindkit.properties.Properties

object XMindProperties : Properties {
    override fun fieldSet(nodeP: SimpleObjectProperty<Node>) =
            if (nodeP.value is XNode) {
                listOf(Fieldset("XMind信息")
                        .apply {
                            field("Title") {
                                label(nodeP.stringBinding { it?.title })
                            }
                        }
                )
            } else {
                emptyList()
            }
}