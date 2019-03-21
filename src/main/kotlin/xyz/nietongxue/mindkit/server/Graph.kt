package xyz.nietongxue.mindkit.server

data class Node(val id: String, val title: String)
data class Edge(val id: String, val title: String, val from: String, val to: String, val type: String)


typealias N = xyz.nietongxue.mindkit.model.Node

data class Graph(val nodes: List<Node>, val edges: List<Edge>) {
    companion object {
        fun fromNodes(root: N): Graph {
            val ns: List<N> = root.collect {
                it
            }
            val nodes = ns.map {
                Node(it.id, it.title)
            }
            val edges: List<Edge> = ns.flatMap {
                it.children.map { ch ->
                    Edge(it.id + ch.id, "", it.id, ch.id, "child")
                }
            }
            return Graph(nodes, edges)
        }
    }
}