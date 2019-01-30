package xyz.nietongxue.mindkit.model.properties

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.Parent
import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.actions.ActionDescriptor
import xyz.nietongxue.mindkit.application.AppController
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.util.Priority
import xyz.nietongxue.mindkit.util.UIGlobal
import xyz.nietongxue.mindkit.util.scanForInstance

interface PropertiesDescriptor {
    fun fieldSet(nodeP: SimpleObjectProperty<Node>): List<Fieldset>

    companion object {
        fun pros(nodeP: SimpleObjectProperty<Node>): List<Fieldset> =
                scanForInstance(PropertiesDescriptor::class).flatMap {
                    it.fieldSet(nodeP)
                }
    }
}

@Priority(10000)
object PropertiesApp {

    val appController = object : AppController {

        val nodeP = SimpleObjectProperty<Node>()
        var node: Node by nodeP
        override fun process(node: Node) {
            this.node = node
            view.rebuild()
            //default action
            //NOTE default action 本质上应该是一种app。但目前工作地很好，不需要改。
            //setup action view

            val action = ActionDescriptor.default().actions(node).first()
            action.view(node)?.also { view ->
                UIGlobal.resultPane?.apply {
                    children.clear()
                    add(view)
                }
            }
            //
            action.action(node)
        }

        override val view = object : View() {
            fun rebuild() {
                (root as VBox).clear()
                with(root) {
                    form {
                        //NOTE 貌似是构造fieldset比较慢，特别是actionProperties的fieldset，也可能是第一个fieldset。
                        //NOTE 速度可以接受。
                        PropertiesDescriptor.pros(nodeP).forEach {
                            this@form.add(it)
                        }
                    }

                }
            }

            override val root: Parent = VBox()
        }

    }

}