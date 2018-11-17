package xyz.nietongxue.mindkit.view

import javafx.collections.FXCollections
import javafx.collections.ObservableArray
import javafx.collections.ObservableList
import xyz.nietongxue.mindkit.model.Node

class ViewNode(val node: Node, val parent: Node?, val children: ObservableList<ViewNode>, val deep: Int) {
    var colpose: Boolean = false
    var focus: Boolean = false

    companion object {
        fun fromNode(n: Node, parent: Node? = null, depp: Int = 0): ViewNode {
            return ViewNode(n, parent, FXCollections.observableArrayList(n.children.map {
                fromNode(it, n, depp + 1)
            }), depp + 1)
        }
        val emptyRoot= fromNode(Node("_root","/",null, ArrayList()))
    }

    fun addChildren(nodes:List<Node>):ViewNode{
        node.children.addAll(nodes)
        this.children.addAll(nodes.map{
            ViewNode.fromNode(it,this.node,this.deep)
        })
        return this
    }
    fun removeChildren():ViewNode{
        node.children.clear()
        this.children.clear()
        return this
    }
    fun pretty() {
        print("++".repeat(deep))
        print(node.title)
        print("-")
        print(node.note)
        println()
        children.forEach {
            it.pretty()
        }
    }
}