package xyz.nietongxue.mindkit.view

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import javafx.scene.control.TreeItem
import javafx.scene.layout.HBox
import tornadofx.*
import xyz.nietongxue.mindkit.io.XMindFile
import xyz.nietongxue.mindkit.model.MindMap

class DemoTreeViews : View() {
    override val root = HBox()
    val model:TreeModel = TreeModel()

    init {
        with(root) {
            addClass(Styles.wrapper)
            vbox {
                this += label("Based on parent-child relationships")
                this += button("load nodes"){
                    action{
                        model.addXmind()
                    }
                }
                treeview<ViewNode> {
                    root = TreeItem(ViewNode.emptyRoot)
                    root.isExpanded = true
                    cellFormat { text = it.node.title }
                    onUserSelect {
                        println(it)
                    }
                    populate {
                        it.value.children
                    }
                }
            }

        }

    }
}
class MyApp:App(){
    override val primaryView = DemoTreeViews::class

}
fun main(args: Array<String>) {
    launch<MyApp>(args = args)
}
