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
        return Statistics(deep, this.childrenCount + 1, this.descendantsCount + 1 + child.descendantsCount)
    }

    companion object {
        //TODO deep没有实现，
        //TODO 是否要一个始终的repository是个问题。所以root也没有定义好。deep也没有定义好。
        fun fromNode(node: Node,deep:Int = 0): Statistics {
            var re = Statistics(deep, 0, 0)
            node.children.forEach {
                re = re.addChild(fromNode(it,deep+1))
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
                    //TODO 可以什么都不干？那么这个函数是不是应该从相关interface里面去除？
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
                    //TODO 真正要做的是某些功能，比如收藏，直接影响Repository的结构。
                    //TODO 一个list，提供了repository的一些节点，比如root、收藏、可能的app节点、某些类型的局部根（比如一个表，一篇文章，一个xx类型的文件）
                }
            }

        }

    }

}
