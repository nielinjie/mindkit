package xyz.nietongxue.mindkit.view

import javafx.scene.control.TreeItem
import javafx.scene.input.Clipboard
import javafx.scene.layout.HBox
import tornadofx.*
import javafx.scene.input.ClipboardContent


class DemoTreeViews : View() {
    override val root = HBox()
    val model = AppModel()


    init {
        with(root) {
            addClass(Styles.wrapper)
            //源，一个outliner
            vbox {
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
                            model.process(it.node)
                        }
                    }
                    populate {
                        it.value.children
                    }
                }
            }
            //中间处理比如templates
            vbox{
                this += scrollpane {
                    text(model.processorStringProperty)
                }

            }
            //结果
            vbox {
                this += button("copy to clipboard"){
                    action{
                        val clipboard = Clipboard.getSystemClipboard()
                        val content = ClipboardContent()
                        content.putString(model.resultString)
                        clipboard.setContent(content)
                    }
                }
                this += scrollpane {
                    text(model.resultStringProperty)
                }

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
