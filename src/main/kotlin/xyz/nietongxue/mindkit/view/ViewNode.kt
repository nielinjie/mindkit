package xyz.nietongxue.mindkit.view

import xyz.nietongxue.mindkit.model.Node

class ViewNode(val node: Node, val parent: Node?, val children: List<ViewNode>, val deep: Int) {
    var colpose: Boolean = false
    var focus: Boolean = false

    companion object {
        fun fromNode(n: Node, parent: Node? = null, depp: Int = 0): ViewNode {
            return ViewNode(n, parent, n.children.map {
                ViewNode.fromNode(it, n, depp + 1)
            }, depp + 1)
        }
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