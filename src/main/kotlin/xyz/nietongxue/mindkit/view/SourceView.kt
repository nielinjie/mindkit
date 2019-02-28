package xyz.nietongxue.mindkit.view

import javafx.animation.PauseTransition
import javafx.event.EventHandler
import javafx.scene.control.*
import javafx.scene.input.KeyEvent
import javafx.scene.layout.VBox
import javafx.util.Duration
import tornadofx.*
import xyz.nietongxue.mindkit.model.Filters
import xyz.nietongxue.mindkit.model.repository.FolderRepository
import xyz.nietongxue.mindkit.util.*
import xyz.nietongxue.mindkit.view.ViewNode.SearchResult
import java.io.File


class SourceView : View() {
    override val root = VBox()
    val controller: MainController by inject()
    val treeModel = find<TreeModel>()
    //    val favoriteView = find<FavoriteView>()
    val repositoryView = find<RepositoryView>()


    val filterField = TextField()
    var treeView by singleAssign<TreeView<ViewNode>>()
    fun <T> iterTree(item: TreeItem<ViewNode>, p: (TreeItem<ViewNode>) -> T) {
        item.children.forEach {
            iterTree(it, p)
        }
        p(item)
    }

    val history = History<ViewNode>()


    init {
        repositoryView.repositoryP.value = FolderRepository(File("/Users/nielinjie/Desktop"))
        val searchActionDebounce = setupSearchingTextEvent()
        with(root) {
            defaultPadding()
            this.add(
//                    favoriteView.root
                    repositoryView.root
            )
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
                    cellFragment(ViewNodeTreeFragment::class)
                    onUserSelect {
                        controller.selectedNode = it.node
                    }
                    populate {
                        it.value.filteredChildren
                    }

                }

            }
        }
//        setupFavoriteSelectedEvent()
        setupRepositorySelectedEvent()
        setupTreeViewKeymap()
        UIGlobal.treeView = this.treeView
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

//    private fun setupFavoriteSelectedEvent() {
//        favoriteView.onFavoriteSelectedP.value = { favorite ->
//            treeModel.resetRoot()
//            history.clear()
//            history.add(treeModel.root)
//            setupTreeView()
//            treeModel.mount(favorite.sources())
//            treeView.root.isExpanded = true
//        }
//    }

    private fun setupRepositorySelectedEvent() {
        repositoryView.onRepositorySelectedP.value = { repository ->
            treeModel.resetRoot()
            history.clear()
            history.add(treeModel.root)
            setupTreeView()
            treeModel.mount(repository.sources())
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
            if (event.metaAnd("J")) {

                if (history.state().backEnabled) {
                    saveTreeState()
                    history.back()
                    val viewNode = history.current()
                    treeModel.moveRoot(viewNode)
                    setupTreeView()
                }
            }
            if (event.metaAnd("K")) {
                if (history.state().forwardEnabled) {
                    saveTreeState()
                    history.forward()
                    val viewNode = history.current()
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
        iterTree(treeView.root) {
            it.isExpanded = it.value.expanded
        }

    }

    private fun saveTreeState() {
        iterTree(treeView.root) {
            it.value.expanded = it.isExpanded
            //TODO 是否要把焦点状态存储在viewNode中？

//            it.value.focus = treeView.selectedValue?.node?.id == it.value.node.id
        }
    }
}









