package xyz.nietongxue.mindkit.properties

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.Parent
import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.application.AppController
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

    val appController = object :AppController {

        val nodeP = SimpleObjectProperty<Node>()
        var node: Node by nodeP
      override  fun process(node: Node) {
            this.node = node
            view.rebuild()
        }

       override  val view = object : View() {
            fun rebuild() {
                (root as VBox).clear()
                with(root) {
                    form {
                        //TODO 性能优化，这里会失去响应一段事件，可能是class scan比较慢
                        Properties.pros(nodeP).forEach {
                            this@form.add(it)
                        }
                    }

                }
            }

            override val root: Parent = VBox()
        }

    }

}