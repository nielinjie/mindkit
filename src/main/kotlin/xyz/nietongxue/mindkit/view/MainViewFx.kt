package xyz.nietongxue.mindkit.view

import javafx.scene.control.TreeItem
import javafx.scene.layout.HBox
import tornadofx.*

class DemoTreeViews : View() {
    override val root = HBox()
    val model = AppModel()


    init {
        with(root) {
            addClass(Styles.wrapper)

            vbox {
                this += label("Based on parent-child relationships")
                this += button("load nodes") {
                    action {
                        model.treeModel.addXmind()
                    }
                }
                treeview<ViewNode> {
                    root = TreeItem(model.treeModel.root)
                    root.isExpanded = true
                    cellFormat { text = it.node.title }
                    onUserSelect {
                        runAsync {
                            model.generate(it.node)
                        }
                    }
                    populate {
                        it.value.children
                    }
                }
            }
            vbox {
                this += text(model.generatedStringProperty)

            }

        }


    }
}

class MyApp : App() {
    override val primaryView = DemoTreeViews::class

}

fun main(args: Array<String>) {
    launch<MyApp>(args = args)
}
