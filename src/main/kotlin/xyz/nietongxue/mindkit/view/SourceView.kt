package xyz.nietongxue.mindkit.view

import javafx.scene.control.TreeItem
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.util.defaultPadding


class SourceView : View() {
    override val root = VBox()
    val controller: MainController by inject()
    val treeModel = TreeModel()

    val favoriteView: FavoriteView = find()
    val folderView: FolderView = find()

    init {
        favoriteView.favoriteSelected = { favorite ->
            treeModel.root.removeChildren()
            treeModel.mount(favorite.sources())
        }
        with(root) {
            defaultPadding()
            hbox {
                defaultPadding()
                hyperlink("收藏") {
                    action { favoriteView.popOver.show(this) }
                }
                hyperlink("打开") {
                    action {
                        val folder = folderView.openChooser()
                        folder?.let { favoriteView.addFolder(it) }
                    }
                }
            }

            scrollpane {
                isFitToHeight = true
                isFitToWidth = true
                vboxConstraints {
                    this.vGrow = Priority.ALWAYS
                }
                treeview<ViewNode> {
                    root = TreeItem(treeModel.root)
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


}


