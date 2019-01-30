package xyz.nietongxue.mindkit.view

import javafx.animation.PauseTransition
import javafx.event.EventHandler
import javafx.scene.control.TextField
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.input.KeyEvent
import javafx.scene.layout.VBox
import javafx.util.Duration
import tornadofx.*
import xyz.nietongxue.mindkit.model.Filters
import xyz.nietongxue.mindkit.util.History
import xyz.nietongxue.mindkit.util.defaultPadding
import xyz.nietongxue.mindkit.util.growV
import xyz.nietongxue.mindkit.util.metaAnd
import xyz.nietongxue.mindkit.view.ViewNode.SearchResult


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
    val history = History<ViewNode>()

    val folderView: FolderView = find()
    val fileView: FileView = find()

    init {
        val searchActionDebounce = setupSearchingTextEvent()
        with(root) {
            defaultPadding()
            hbox {
                defaultPadding()
                hyperlink("收藏") {
                    action { favoriteView.popOver.show(this) }
                }
                hyperlink("打开目录") {
                    action {
                        val folder = folderView.openChooser()
                        folder?.let { favoriteView.addFolder(it) }
                    }
                }
                hyperlink("打开文件") {
                    action {
                        val file = fileView.openChooser()
                        file?.let { favoriteView.addFile(it) }
                    }
                }
            }
            this.add(filterField)
            filterField.textProperty().onChange {
                searchActionDebounce.playFromStart()
            }
            scrollpane {
                isFitToHeight = true
                isFitToWidth = true
                growV()
                treeView = treeview {
                    root = TreeItem(treeModel.root)
                    root.isExpanded = true
                    cellFormat {
                        text = it.node.title
                        opacity = when (it.searchResult) {
                            SearchResult.CHILD -> 0.5
                            else -> 1.0
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
        setupFavoriteSelectedEvent()
        setupTreeViewKeymap()
    }

    private fun setupSearchingTextEvent(): PauseTransition {
        val searchActionDebounce = PauseTransition(Duration.seconds(1.0))
        searchActionDebounce.setOnFinished {
            val filterS = filterField.textProperty().value
            if (filterS?.let { it.length > 1 } == true) {
                treeModel.root.filter = Filters.filter(filterS)
            } else {
                treeModel.root.filter = null
            }
            //
            iterTree(treeView.root) {
                if (it.value.searchResult == SearchResult.CHILD_AND_SELF
                        || it.value.searchResult == SearchResult.CHILD)
                    it.isExpanded = true
            }
        }
        return searchActionDebounce
    }

    private fun setupFavoriteSelectedEvent() {
        favoriteView.onFavoriteSelectedP.value = { favorite ->
            treeModel.resetRoot()
            history.clear()
            history.add(treeModel.root)
            setupTreeView()
            treeModel.mount(favorite.sources())
            treeView.root.isExpanded = true
        }
    }

    private fun setupTreeViewKeymap() {
        treeView.onKeyReleased = EventHandler<KeyEvent> { event ->
            if (event.metaAnd("Right")) {
                treeView.selectionModel.selectedItem.expandAll()
            }
            if (event.metaAnd("F")) {
                saveTreeState()
                val viewNode = treeView.selectionModel.selectedItem.value
                history.add(viewNode)
                treeModel.moveRoot(viewNode)
                setupTreeView()
                treeView.selectFirst()
            }
            if(event.metaAnd("J")){

                if(history.state().backEnabled){
                    saveTreeState()
                    history.back()
                    val viewNode =history.current()
                    treeModel.moveRoot(viewNode)
                    setupTreeView()
                }
            }
            if(event.metaAnd("K")){
                if(history.state().forwardEnabled){
                    saveTreeState()
                    history.forward()
                    val viewNode =history.current()
                    treeModel.moveRoot(viewNode)
                    setupTreeView()
                }
            }

        }
    }

    private fun setupTreeView() {
        with(treeView) {
            root = TreeItem(treeModel.root)
            populate {
                it.value.filteredChildren
            }

        }
        iterTree(treeView.root){
            it.isExpanded = it.value.expanded
        }

    }
    private fun saveTreeState(){
        iterTree(treeView.root){
            it.value.expanded = it.isExpanded
            //TODO 把焦点状态存储在viewNode中。

//            it.value.focus = treeView.selectedValue?.node?.id == it.value.node.id
        }
    }
}





