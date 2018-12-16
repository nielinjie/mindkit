package xyz.nietongxue.mindkit.view

import com.beust.klaxon.internal.firstNotNullResult
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.source.Mounting
import xyz.nietongxue.mindkit.source.Source

class ViewNode(val node: Node, val parent: Node?, val children: ObservableList<ViewNode>, val deep: Int) {
    var collapse: Boolean = false
    var focus: Boolean = false

    companion object {
        fun fromNode(n: Node, parent: Node? = null, deep: Int = 0): ViewNode {
            return ViewNode(n, parent, FXCollections.observableArrayList(n.children.map {
                fromNode(it, n, deep + 1)
            }), deep + 1)
        }

        //TODO empty node
        val emptyRoot = ViewNode.fromNode(object : Node {
            override val id: String = "_root"
            override val title: String = "/"
            override val children: ArrayList<Node> = ArrayList()
            override val source: Source = object : Source {
                override val description: String = "内置"
                override fun mount(tree: Node, mountPoint: Node): List<Mounting> = emptyList()
            }
        })
    }

    fun findNode(node: Node): ViewNode? {
        //TODO 按值寻找
        return if (this.node == node)
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