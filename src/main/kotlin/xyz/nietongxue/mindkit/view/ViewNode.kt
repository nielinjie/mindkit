package xyz.nietongxue.mindkit.view

import com.beust.klaxon.internal.firstNotNullResult
import javafx.collections.FXCollections
import javafx.collections.FXCollections.observableArrayList
import javafx.collections.ObservableList
import tornadofx.observableList
import tornadofx.onChange
import xyz.nietongxue.mindkit.model.Filter
import xyz.nietongxue.mindkit.model.Marker
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.source.InternalSource
import xyz.nietongxue.mindkit.model.source.Source

class ViewNode(val node: Node, val parent: Node?, val children: ObservableList<ViewNode>) {
    var expanded: Boolean = false
    var focus: Boolean = false
    val descendantsMarkersCache: MutableList<Marker> = mutableListOf()


    var filterResult: FilterResult = FilterResult.NONE







    val filteredChildren: ObservableList<ViewNode> = observableList()



    companion object {
        fun fromNode(n: Node, parent: Node? = null): ViewNode {
            val children = FXCollections.observableArrayList(n.children.map {
                fromNode(it, n)
            })
            val re = ViewNode(n, parent, children)
            re.descendantsMarkersCache.addAll(children.flatMap {
                it.node.markers + it.descendantsMarkersCache
            }.distinct())
            return re
        }

        fun emptyRoot() = ViewNode.fromNode(object : Node {
            override val markers: MutableList<Marker> = mutableListOf()
            override val id: String = "_root"
            override var title: String = "/"
            override val children: ArrayList<Node> = ArrayList()
            override val source: Source = InternalSource
        })
    }

    fun findNode(id: String): ViewNode? {
        //按值寻找
        return if (this.node.id
                == id)
            this
        else
            this.children.firstNotNullResult { it.findNode(id) }
    }

    fun replaceChildren(nodes: List<Node>): ViewNode {
        this.removeChildren()
        return this.addChildren(nodes)
    }

    fun addChildren(nodes: List<Node>, afterNode: Node? = null): ViewNode {
        val ch = nodes.map {
            ViewNode.fromNode(it, this.node)
        }
        if (afterNode != null) {
            require(nodes.size == 1) { "not implemented yet." }
            val brotherIndex = this.children.indexOfFirst { it.node.id == afterNode.id }
            require(brotherIndex != -1)
            node.children.add(brotherIndex + 1, nodes.first())
            this.children.add(brotherIndex + 1, ch.first())
        } else {
            node.children.addAll(nodes)
            this.children.addAll(ch)
        }

        this.descendantsMarkersCache.clear()
        this.descendantsMarkersCache.addAll(ch.flatMap { it.node.markers + it.descendantsMarkersCache }.distinct())
        return this
    }


    //TODO copy 的代码。
    fun insertChildren(nodes: List<Node>, beforeNode: Node? = null): ViewNode {
        val ch = nodes.map {
            ViewNode.fromNode(it, this.node)
        }
        if (beforeNode != null) {
            require(nodes.size == 1) { "not implemented yet." }
            val brotherIndex = this.children.indexOfFirst { it.node.id == beforeNode.id }
            require(brotherIndex != -1)
            node.children.add(brotherIndex, nodes.first())
            this.children.add(brotherIndex, ch.first())
        } else {
            node.children.add(0, nodes.first())
            this.children.add(0, ch.first())
        }
        this.descendantsMarkersCache.clear()
        this.descendantsMarkersCache.addAll(ch.flatMap { it.node.markers + it.descendantsMarkersCache }.distinct())
        return this
    }

    fun removeChildren(): ViewNode {
        node.children.clear()
        this.children.clear()
        this.descendantsMarkersCache.clear()
        return this
    }

    fun iteratorViewNode(fn: (ViewNode) -> Unit) {
        this.children.forEach {
            it.iteratorViewNode(fn)
        }
        fn(this)
    }

    fun findParent(root: ViewNode): ViewNode? {
        return this.parent?.id?.let { root.findNode(it) }
    }
}