package xyz.nietongxue.mindkit.application

import javafx.scene.layout.VBox
import org.reflections.Reflections
import tornadofx.View
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.Function


interface AppDescriptor {
    val name: String
    val description: String
    //TODO 有没有必要从tornadofx（或者其他什么view 技术）解耦合？
    val providedFunctions: List<Function>
    val controller: Controller

    companion object {
        val nonApp: AppDescriptor = object : AppDescriptor {
            override val controller: Controller = object : Controller {
                override fun process(node: Node) {


                }

                override val view: View
                    get() = object : View() {
                        override val root = VBox()
                    }
                override var function: Function? = null
            }
            override val name: String
                get() = "None"
            override val description: String
                get() = "None"

            override val providedFunctions: List<Function> by lazy {
                listOf(nonProcessor)
            }

        }
        private val nonProcessor = object : Function {
            override val brief = "None"
            override val description: String
                get() = "什么都不做的一个处理器"

            override fun process(node: Node): String {
                return ""
            }

        }
        val all: List<AppDescriptor> by lazy {
            val reflections = Reflections("xyz.nietongxue.mindkit.application.*")
            val descriptors = reflections.getSubTypesOf(AppDescriptor::class.java)
            val instances = descriptors.map {
                it.kotlin.objectInstance
            }.toList().filterNotNull()
                    //nonApp好像在filterNotNull的时候被滤掉了。
                    //应该是因为object：AppDescriptor这种形式不会产生kotlin object，而是产生一个class
                    .filterNot { it== nonApp }
            return@lazy listOf(nonApp) + instances
        }


    }
}
interface Controller{
    fun process(node:Node)
    var function: Function?

    val view: View
}