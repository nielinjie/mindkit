package xyz.nietongxue.mindkit.view

import javafx.scene.control.TextField
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.util.defaultPadding
import javafx.animation.PauseTransition
import javafx.util.Duration


class SourceView : View() {
    override val root = VBox()
    val controller: MainController by inject()
    val treeModel = find<TreeModel>()
    val favoriteView = find<FavoriteView>()


    val filterField = TextField()
    var treeView by singleAssign<TreeView<ViewNode>>()
    fun <T> iterTree(item: TreeItem<ViewNode>, p: (TreeItem<ViewNode>) -> T) {
        item.children.forEach {
            iterTree(it, p)
        }
        p(item)
    }


    val folderView: FolderView = find()

    init {


        val searchActionDebounce = PauseTransition(Duration.seconds(1.0))
        searchActionDebounce.setOnFinished { e ->
            val filterS = filterField.textProperty().value
            if (filterS?.let { it.length > 1 } == true) {
                treeModel.root.filter = {
                    it.node.title.contains(filterS ?: "")
                }
            } else {
                treeModel.root.filter = null
            }
            //
            iterTree(treeView.root) {
                if (it.value.searchResult == xyz.nietongxue.mindkit.view.ViewNode.SearchResult.CS
                        || it.value.searchResult == xyz.nietongxue.mindkit.view.ViewNode.SearchResult.CHILD)
                    it.isExpanded = true
            }
        }


        favoriteView.onFavoriteSelectedP.value = { favorite ->
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
            this.add(filterField)
            filterField.textProperty().onChange { filterS ->
                searchActionDebounce.playFromStart()
            }
            scrollpane {
                isFitToHeight = true
                isFitToWidth = true
                vboxConstraints {
                    this.vGrow = Priority.ALWAYS
                }


                treeView = treeview {
                    root = TreeItem(treeModel.root)
                    root.isExpanded = true
                    cellFormat {
                        text = it.node.title
                        opacity = when (it.searchResult) {
                            ViewNode.SearchResult.SELF -> 1.0
                            ViewNode.SearchResult.CHILD -> 0.5
                            ViewNode.SearchResult.NONE -> 1.0
                            ViewNode.SearchResult.CS -> 1.0
                        }

                    }
                    onUserSelect {
                        controller.selectedNode = it.node
                    }
                    populate {
                        it.value.filteredChildren
                    }
                }

            }
        }
    }
}





