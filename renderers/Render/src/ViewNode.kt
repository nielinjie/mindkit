package xyz.nietongxue.mindkit.view

import xyz.nietongxue.mindkit.model.Node

class ViewNode(val node: Node, val parent:Node?,val children: List<ViewNode>, val deep: Int) {
    var colpose: Boolean = false
    var focus: Boolean = false

    companion object {
        fun fromNode(n: Node,p:Node?=null, d: Int = 0): ViewNode {
            return ViewNode(n,p, n.children.map {
                ViewNode.fromNode(it, n,d + 1)
            }, d + 1)
        }
    }

    fun pretty() {
        print("++".repeat(deep))
        println(node.title)
        children.forEach {
            it.pretty()
        }
    }
}