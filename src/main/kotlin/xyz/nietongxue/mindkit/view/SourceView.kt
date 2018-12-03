package xyz.nietongxue.mindkit.view

import javafx.scene.control.TreeItem
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*

class SourceView : View() {
    override val root = VBox()
    val controller: MainController by inject()

    init {
        with(root) {
            this += button("load nodes") {
                action {
                    controller.treeModel.addXmind()
                }
            }
            scrollpane {
                isFitToHeight = true;
                isFitToWidth = true;
                vboxConstraints {
                    this.vGrow = Priority.ALWAYS
                }
                treeview<ViewNode> {
                    root = TreeItem(controller.treeModel.root)
                    root.isExpanded = true
                    cellFormat { text = it.node.title }
                    onUserSelect {
                        runAsync {
                            controller.process(it.node)
                        }
                    }
                    populate {
                        it.value.children
                    }
                }
            }

        }

    }
}

