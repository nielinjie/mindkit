package xyz.nietongxue.mindkit.properties

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.Parent
import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.application.AppDescriptor
import xyz.nietongxue.mindkit.application.AppController
import xyz.nietongxue.mindkit.model.Function
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.XNode
import xyz.nietongxue.mindkit.util.scanForInstance

interface Properties {
    fun fieldSet(nodeP: SimpleObjectProperty<Node>): List<Fieldset>

    companion object {
        fun pros(nodeP: SimpleObjectProperty<Node>): List<Fieldset> =
                scanForInstance(Properties::class).flatMap {
                    it.fieldSet(nodeP)
                }
    }
}

object PropertiesApp : AppDescriptor {
    override val name: String = "Properties"
    override val description: String = "Properties of node"
    override val providedFunctions: List<Function> = listOf(
            object : Function {
                override fun process(node: Node): String {
                    //TODO 可以什么都不干？那么这个函数是不是应该从相关interface里面去除？
                    return ""
                }

                override val brief: String = "Properties"
                override val description: String = "Properties of node"
            }
    )
    override val appController: AppController = object : AppController {

        val nodeP = SimpleObjectProperty<Node>()
        var node: Node by nodeP
        override fun process(node: Node) {
            //FIXME

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

                    //TODO Action 机制，提示在此节点，或者此节点"附近"可以做点啥。
                }
            }

        }

    }

}