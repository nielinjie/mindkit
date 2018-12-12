package xyz.nietongxue.mindkit.view

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonValue
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import xyz.nietongxue.mindkit.model.Node

class ViewNode(val node: Node, val parent: Node?, val children: ObservableList<ViewNode>, val deep: Int) {
    var collapse: Boolean = false
    var focus: Boolean = false

    companion object {
        fun fromNode(n: Node, parent: Node? = null, deep: Int = 0): ViewNode {
            return ViewNode(n, parent, FXCollections.observableArrayList(n.children.map {
                fromNode(it, n, deep + 1)
            }), deep + 1)
        }
        val emptyRoot= fromNode(Node("_root","/", emptyList(),null, JsonArray(), ArrayList()))
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