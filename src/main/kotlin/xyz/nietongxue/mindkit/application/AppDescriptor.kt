package xyz.nietongxue.mindkit.application

import javafx.scene.layout.VBox
import org.reflections.Reflections
import tornadofx.View
import xyz.nietongxue.mindkit.properties.StatisticsApp
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
                    .filterNot { it == StatisticsApp }
            return@lazy listOf(StatisticsApp) + instances
        }


    }
}
interface Controller{
    //TODO 到后台去运行，不能单用runAsync，因为他引起的ui的变化不在ui thread，需要在ui block。现在能做的是在实现的时候处理。
    fun process(node:Node)
    var function: Function?
    val view: View
}