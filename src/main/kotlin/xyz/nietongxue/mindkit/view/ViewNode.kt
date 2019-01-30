package xyz.nietongxue.mindkit.view

import com.beust.klaxon.internal.firstNotNullResult
import javafx.collections.FXCollections
import javafx.collections.FXCollections.observableArrayList
import javafx.collections.ObservableList
import tornadofx.onChange
import xyz.nietongxue.mindkit.model.Filter
import xyz.nietongxue.mindkit.model.Marker
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.source.InternalSource
import xyz.nietongxue.mindkit.model.source.Source

class ViewNode(val node: Node, val parent: Node?, val children: ObservableList<ViewNode>, val deep: Int) {
    var expanded: Boolean = false
    var focus: Boolean = false


    enum class SearchResult { CHILD_AND_SELF, SELF, CHILD, NONE

    }

    var searchResult: SearchResult = SearchResult.NONE

    private fun filteredChildren(): List<ViewNode> {
        return if (filter === null) {
            this.children
        } else {
            children.filter {
                filter!!.let { it1 -> it1(it.node) } || it.filteredChildren().isNotEmpty()
            }
        }
    }

    private fun setSearchResult() {
        if(filter == null) {
            this.searchResult =  SearchResult.NONE
            return
        }
        val self: Boolean = (filter?.let { it -> it(this.node) } == true)
        val child: Boolean = (children.any {
            it.searchResult != SearchResult.NONE
        })
        this.searchResult = when (self to child) {
            (true to true) -> SearchResult.CHILD_AND_SELF
            (true to false) -> SearchResult.SELF
            (false to true) -> SearchResult.CHILD
            (false to false) -> SearchResult.NONE
            else -> {
                throw IllegalStateException()
            }
        }


    }

    var filter: Filter? = null
        set(value) {
            field = value
            children.forEach {
                it.filter = value
            }
            this.filteredChildren.setAll(filteredChildren())
            setSearchResult()
        }

    val filteredChildren: ObservableList<ViewNode> = observableArrayList(children)

    init {
        children.onChange {
            this.filteredChildren.setAll(filteredChildren())
            setSearchResult()
        }
    }

    companion object {
        fun fromNode(n: Node, parent: Node? = null, deep: Int = 0): ViewNode {
            return ViewNode(n, parent, FXCollections.observableArrayList(n.children.map {
                fromNode(it, n, deep + 1)
            }), deep + 1)
        }

        fun emptyRoot() = ViewNode.fromNode(object : Node {
            override val markers: MutableList<Marker>
                = mutableListOf()
            override val id: String = "_root"
            override val title: String = "/"
            override val children: ArrayList<Node> = ArrayList()
            override val source: Source = InternalSource
        })
    }

    fun findNode(node: Node): ViewNode? {
        //按值寻找
        return if (this.node.id
                == node.id)
            this
        else
            this.children.firstNotNullResult { it.findNode(node) }
    }

    fun replaceChildren(nodes: List<Node>): ViewNode {
        this.removeChildren()
        return this.addChildren(nodes)
    }

    fun addChildren(nodes: List<Node>): ViewNode {
        node.children.addAll(nodes)
        this.children.addAll(nodes.map {
            ViewNode.fromNode(it, this.node, this.deep)
        })
        return this
    }

    fun removeChildren(): ViewNode {
        node.children.clear()
        this.children.clear()
        return this
    }

}