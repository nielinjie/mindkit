package xyz.nietongxue.mindkit.view

import javafx.scene.control.TreeItem
import javafx.scene.layout.VBox
import tornadofx.*

class SourceView : View(){
    override val root= VBox()
    val controller:MainController by inject()

    init {
        with(root) {
            this += button("load nodes") {
                action {
                    controller.treeModel.addXmind()
                }
            }
            treeview<ViewNode> {
                root = TreeItem(controller.treeModel.root)
                root.isExpanded = true
                cellFormat { text = it.node.title }
                onUserSelect {
                    controller.selectedNode = it.node
                }
                populate {
                    it.value.children
                }
            }

        }

    }
}

