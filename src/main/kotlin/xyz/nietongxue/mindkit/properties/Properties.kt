package xyz.nietongxue.mindkit.properties

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.Parent
import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.application.AppController
import xyz.nietongxue.mindkit.model.Function
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.util.Priority
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
@Priority(10000)
object PropertiesApp  {
     val providedFunctions: List<Function> = listOf(
            object : Function {
                override fun process(node: Node): String {
                    //TODO 可以什么都不干？那么这个函数是不是应该从相关interface里面去除？
                    return ""
                }

                override val brief: String = "Properties"
                override val description: String = "Properties of node"
            }
    )
    val name: String = "Properties"
    val description: String = "Properties of node"

    val appController = object :AppController {

        val nodeP = SimpleObjectProperty<Node>()
        var node: Node by nodeP
      override  fun process(node: Node) {
            this.node = node
            view.rebuild()
        }

       override var function: Function? = null
       override  val view = object : View() {
            fun rebuild() {
                (root as VBox).clear()
                with(root) {
                    form {
                        Properties.pros(nodeP).forEach {
                            this@form.add(it)
                        }
                    }

                }
            }

            override val root: Parent = VBox()

            init {

            }

        }

    }

}