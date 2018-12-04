package xyz.nietongxue.mindkit.application

import javafx.scene.layout.VBox
import tornadofx.View
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.Processor

interface AppDescriptor {
    val name: String
    val description: String
    //TODO 有没有必要从tornadofx（或者其他什么view 技术）解耦合？
    val providedProcessors: List<Processor>
    val controller:Controller
    //TODO App和Processor的只能需要再捋一下，不够明确
    companion object {

        val nonApp: AppDescriptor = object : AppDescriptor {
            override val controller: Controller = object :Controller{
                override fun process(node: Node) {


                }
                override val view: View
                    get() = object : View() {
                        override val root = VBox()
                    }
                override var processor:Processor? = null
            }
            override val name: String
                get() = "None"
            override val description: String
                get() = "None"

            override val providedProcessors: List<Processor> by lazy {
                listOf(nonProcessor)
            }

        }
        private val nonProcessor = object : Processor {
            override val brief = "None"
            override val description: String
                get() = "什么都不做的一个处理器"

            override fun process(node: Node): String {
                return ""
            }

            override val app: AppDescriptor = nonApp
        }

    }
}
interface Controller{
    fun process(node:Node):Unit
    var processor:Processor?
    val view: View
}