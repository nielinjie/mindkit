package xyz.nietongxue.mindkit.properties

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.Parent
import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.application.AppDescriptor
import xyz.nietongxue.mindkit.application.AppController
import xyz.nietongxue.mindkit.model.Function
import xyz.nietongxue.mindkit.model.Node

interface Properties{
    fun fieldSet(nodeP:SimpleObjectProperty<Node>):List<Fieldset>
    companion object {
        fun pros(nodeP:SimpleObjectProperty<Node>):List<Fieldset> =
                listOf(XMindProperties,PositionProperties).flatMap {
            it.fieldSet(nodeP)
        }
    }
}

object PropertiesApp : AppDescriptor {
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
    override val appController: AppController = object : AppController {

        val nodeP = SimpleObjectProperty<Node>()
        var node: Node by nodeP
        override fun process(node: Node) {
            runAsync {
                node
            } ui {
                this.node = it
            }
        }

        override var function: Function? = null
        override val view: View = object : View() {
            override val root: Parent = VBox()

            init {
                with(root) {
                    form {

                        Properties.pros(nodeP).forEach {
                            this@form.add(it)
                        }
                    }

                    //TODO 真正要做的是某些功能，比如收藏，直接影响Repository的结构。
                    //TODO 一个list，提供了repository的一些节点，比如root、收藏、可能的app节点、某些类型的局部根（比如一个表，一篇文章，一个xx类型的文件）
                }
            }

        }

    }

}