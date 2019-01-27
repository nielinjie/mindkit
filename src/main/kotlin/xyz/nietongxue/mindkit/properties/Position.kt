package xyz.nietongxue.mindkit.properties

import javafx.beans.property.SimpleObjectProperty
import tornadofx.Fieldset
import tornadofx.field
import tornadofx.label
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.util.Priority


data class Position(val deep: Int, val childrenCount: Int, val descendantsCount: Int) {
    fun addChild(child: Position): Position {
        return Position(deep, this.childrenCount + 1, this.descendantsCount + 1 + child.descendantsCount)
    }

    companion object {
        //TODO deep没有实现，不过好像也没有什么用。
        fun fromNode(node: Node, deep: Int = 0): Position {
            var re = Position(deep, 0, 0)
            node.children.forEach {
                re = re.addChild(fromNode(it, deep + 1))
            }
            return re
        }

    }
}
@Priority(-10)
object PositionPropertiesDescriptor : PropertiesDescriptor {
    override fun fieldSet(nodeP: SimpleObjectProperty<Node>): List<Fieldset> {
        val position = Position.fromNode(nodeP.value)
        val re =
                Fieldset("位置信息")
        with(re) {
            field("Deep") {
                label(position.deep.toString())
            }
            field("Children") {
                label(position.childrenCount.toString())
            }
            field("Descendant") {
                label(position.descendantsCount.toString())
            }
        }
        return listOf(re)

    }
}


