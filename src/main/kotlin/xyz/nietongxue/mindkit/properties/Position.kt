package xyz.nietongxue.mindkit.properties

import javafx.beans.property.SimpleObjectProperty
import tornadofx.Fieldset
import tornadofx.field
import tornadofx.label
import tornadofx.stringBinding
import xyz.nietongxue.mindkit.application.xmind.Node
import xyz.nietongxue.mindkit.util.Priority


data class Position(val deep: Int, val childrenCount: Int, val descendantsCount: Int) {
    fun addChild(child: Position): Position {
        return Position(deep, this.childrenCount + 1, this.descendantsCount + 1 + child.descendantsCount)
    }

    companion object {
        //TODO deep没有实现，
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
object PositionProperties : Properties {
    override fun fieldSet(nodeP: SimpleObjectProperty<Node>): List<Fieldset> {
        val re =
                Fieldset("位置信息")
        with(re) {
            field("Deep") {
                label(nodeP.stringBinding { it?.let { Position.fromNode(it) }?.deep.toString() })
            }
            field("Children") {
                label(nodeP.stringBinding { it?.let { Position.fromNode(it) }?.childrenCount.toString() })
            }
            field("Descendant") {
                label(nodeP.stringBinding { it?.let { Position.fromNode(it) }?.descendantsCount.toString() })
            }
        }
        return listOf(re)

    }
}

@Priority(-5)
object SourceProperties : Properties {
    override fun fieldSet(nodeP: SimpleObjectProperty<Node>): List<Fieldset> {
        val re =
                Fieldset("来源信息")
        with(re) {
            field("来源") {
                //TODO wrap 没有起作用
                label(nodeP.stringBinding { it?.source?.description ?: "（不明）"}){
                    isWrapText = true
                }
            }
        }
        return listOf(re)

    }
}

