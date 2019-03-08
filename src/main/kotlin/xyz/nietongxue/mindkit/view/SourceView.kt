package xyz.nietongxue.mindkit.view

import javafx.event.EventHandler
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.VBox
import tornadofx.*
import xyz.nietongxue.mindkit.model.Filters
import xyz.nietongxue.mindkit.model.repository.FolderRepository
import xyz.nietongxue.mindkit.model.repository.SimpleTextNode
import xyz.nietongxue.mindkit.model.source.EditableSource
import xyz.nietongxue.mindkit.util.*
import xyz.nietongxue.mindkit.view.ViewNode.SearchResult
import java.io.File
import java.util.*


class SourceView : View() {
    override val root = VBox()
    private val controller: MainController by inject()
    private val treeModel = find<TreeModel>()
    //    val favoriteView = find<FavoriteView>()
    val repositoryView = find<RepositoryView>()
    private val searchView = find<SearchView>()


    var treeView by singleAssign<TreeView<ViewNode>>()
    private fun <T> iterateTree(item: TreeItem<ViewNode>, p: (TreeItem<ViewNode>) -> T) {
        item.children.forEach {
            iterateTree(it, p)
        }
        p(item)
    }

    private val history = History<ViewNode>()


    init {
        //TODO repository选择和保存最后一个
        repositoryView.repositoryP.value = FolderRepository(File("/Users/nielinjie/Desktop/testRepository/"))
        with(root) {
            defaultPadding()
            this.add(
//                  favoriteView.root
                    repositoryView.root
            )
            this.add(
                    searchView.root
            )
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

//      setupFavoriteSelectedEvent()
        setupRepositorySelectedEvent()
        setupTreeViewKeymap()
        setupSearchChangeEvent()

        UIGlobal.treeView = this.treeView
    }

    private fun setupSearchChangeEvent() {
        searchView.onChange = { filterS ->
            if (filterS.length > 1) {
                treeModel.root.filter = Filters.filter(filterS)
            } else {
                treeModel.root.filter = null
            }
            iterateTree(treeView.root) {
                if (it.value.searchResult == SearchResult.CHILD_AND_SELF
                        || it.value.searchResult == SearchResult.CHILD)
                    it.isExpanded = true
            }
        }
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
            if (event.code == KeyCode.F2) {
                editNode()
            }


            if (event.code == KeyCode.MINUS) {
                //add following brother
                treeView.selectedValue?.also {
                    val node = it.node
                    val parentNode = it.parent
                    (parentNode?.source as? EditableSource)?.let { editableSource ->
                        val newNode = SimpleTextNode.fromText(Date().shortString(), editableSource)
                        editableSource.add(parentNode, node, newNode)
                        treeView.selectionModel.selectedItem.parent.value.addChildren(listOf(newNode), it.node)
                        runLater {
                            treeView.findItem { it.node.id == newNode.id }?.let {
                                treeView.expandToItem(it.parent)
                                treeView.selectionModel.select(it)
                                editNode()
                            }
                        }

                    }
                }
            }
            if (event.code == KeyCode.EQUALS) { //KeyCode.TAB) {
                //add child
                treeView.selectedValue?.also {
                    val parentNode = it.node
                    (parentNode.source as? EditableSource)?.let { editableSource ->
                        val newNode = SimpleTextNode.fromText(Date().shortString(), editableSource)
                        editableSource.add(parentNode, null, newNode)
                        treeView.selectionModel.selectedItem.value.insertChildren(listOf(newNode))
                        runLater {
                            treeView.findItem { it.node.id == newNode.id }?.let {
                                treeView.expandToItem(it.parent)
                                treeView.selectionModel.select(it)
                                editNode()
                            }
                        }
                    }

                }
            }

        }
    }

    private fun editNode() {
        val node = treeView.selectedValue?.node
        val parent = treeView.selectedValue?.parent
        node?.let {
            nodeDialog(it.title)?.let { newTitle ->
                val source = it.source as? EditableSource
                it.title = newTitle
                source?.also { es ->
                    es.edit(parent!!, it)
                    runLater {
                        treeView.refresh()
                    }
                }
            }
        }
    }

    private fun setupTreeView() {
        with(treeView) {
            this.isEditable = true
            root = TreeItem(treeModel.root)
            populate {
                it.value.filteredChildren
            }
        }
        iterateTree(treeView.root) {
            it.isExpanded = it.value.expanded
        }
    }

    private fun saveTreeState() {
        iterateTree(treeView.root) {
            it.value.expanded = it.isExpanded
            //TODO 是否要把焦点状态存储在viewNode中？
        }
    }
}









