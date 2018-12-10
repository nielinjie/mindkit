package xyz.nietongxue.mindkit.application.properties

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.Parent
import javafx.scene.layout.VBox
import tornadofx.View
import tornadofx.runAsync
import tornadofx.ui
import xyz.nietongxue.mindkit.application.AppDescriptor
import xyz.nietongxue.mindkit.application.Controller
import xyz.nietongxue.mindkit.model.Function
import xyz.nietongxue.mindkit.model.Node
import tornadofx.*


data class Statistics(val deep: Int, val childrenCount: Int, val descendantsCount: Int) {
    fun addChild(child: Statistics): Statistics {
        return Statistics(child.deep, this.childrenCount + 1, this.descendantsCount + 1 + child.descendantsCount)
    }

    companion object {
        fun fromNode(node: Node): Statistics {
            var re = Statistics(0, 0, 0)
            node.children.forEach {
                re = re.addChild(fromNode(it))
            }
            return re
        }

    }
}


object StatisticsApp : AppDescriptor {
    override val name: String = "Properties"
    override val description: String = "Properties of Node"
    override val providedFunctions: List<Function> = listOf(
            object : Function {
                override fun process(node: Node): String {
                    //TODO 可以什么都不干？
                    return ""
                }
                override val brief: String = "Properties"
                override val description: String = "Properties of Node"
            }
    )
    override val controller: Controller = object : Controller {
        val staP = SimpleObjectProperty<Statistics>()
        var statistics: Statistics by staP
        override fun process(node: Node) {
            runAsync {
                Statistics.fromNode(node)
            } ui {
                statistics = it
            }
        }

        override var function: Function? = null
        override val view: View = object : View() {
            override val root: Parent = VBox()

            init {
                with(root) {
                    label(staP.asString())
                }
            }

        }

    }

}
