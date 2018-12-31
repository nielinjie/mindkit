package xyz.nietongxue.mindkit.view

import javafx.scene.control.TextField
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.util.defaultPadding


class SourceView : View() {
    override val root = VBox()
    val controller: MainController by inject()
    val treeModel = find<TreeModel>()
    val favoriteView = find<FavoriteView>()


    val filterField = TextField()
    var treeView by singleAssign<TreeView<ViewNode>>()
    fun  <T> iterTree(item:TreeItem<ViewNode>,p:(TreeItem<ViewNode>)-> T){
        item.children.forEach{
           iterTree(it,p)
        }
        p(item)
    }

    init {


        with(root) {
            defaultPadding()
            hyperlink("收藏") {
                action { favoriteView.popover.show(this) }
            }
            this.add(filterField)
            filterField.textProperty().onChange { filterS ->
                if (filterS?.let { it.length > 1 } == true) {
                    treeModel.root.filter = {
                        it.node.title.contains(filterS ?: "")
                    }
                } else {
                    treeModel.root.filter = null
                }
                //
                iterTree(treeView.root) {
                    if(it.value.searchResult== ViewNode.SearchResult.CS || it.value.searchResult == ViewNode.SearchResult.CHILD)
                        it.isExpanded = true
                }

            }
            scrollpane {
                isFitToHeight = true
                isFitToWidth = true
                vboxConstraints {
                    this.vGrow = Priority.ALWAYS
                }


                treeView = treeview<ViewNode> {
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


