package xyz.nietongxue.mindkit.properties

import javafx.beans.property.SimpleObjectProperty
import tornadofx.Fieldset
import tornadofx.field
import tornadofx.label
import tornadofx.stringBinding
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.util.Priority

@Priority(-5)
object SourceProperties : Properties {
    override fun fieldSet(nodeP: SimpleObjectProperty<Node>): List<Fieldset> {
        val re =
                Fieldset("来源信息")
        with(re) {
            field{
                //TODO wrap 没有起作用
                label(nodeP.stringBinding { it?.source?.description ?: "（不明）"}){
                    isWrapText = true
                }
            }
        }
        return listOf(re)

    }
}