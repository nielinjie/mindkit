package xyz.nietongxue.mindkit.view

import javafx.scene.control.TextField
import javafx.scene.control.TreeItem
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.util.defaultPadding


class SourceView : View() {
    override val root = VBox()
    val controller: MainController by inject()
    val treeModel = find<TreeModel>()
    val favoriteView = find<FavoriteView> ()


    val filterField = TextField()

    init {



        with(root) {
            defaultPadding()
            hyperlink("收藏") {
                action { favoriteView.popover.show(this) }
            }
            this.add(filterField)
            filterField.textProperty().onChange { filterS  ->
                if(filterS?.let{it.length>1 } == true) {
                    treeModel.root.filter = {
                        it.node.title.contains(filterS ?: "")
                    }
                }else{
                    treeModel.root.filter = null
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
                        it.value.filteredChildren
                    }
                }

            }
        }
    }




}


