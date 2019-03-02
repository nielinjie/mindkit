package xyz.nietongxue.mindkit.model.repository

import xyz.nietongxue.mindkit.model.Markers
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.repository.NodeRecorder.Companion.fromNodes
import xyz.nietongxue.mindkit.model.source.InternalSource
import xyz.nietongxue.mindkit.model.source.Source
import xyz.nietongxue.mindkit.util.FileJsonStore
import java.io.File

//TODO 存储更丰富的内容。
data class NodeRecorder(val id: String, val title: String, val markers: List<String>, val parentId: String) {
    init {
        require(parentId != id)
    }

    fun toNode(source: Source): Node {
        return SimpleTextNode(
                id, title, mutableListOf(), markers.mapNotNull {
            Markers.byName(it)
        }.toMutableList(), source)

    }

    companion object {
        //返回node和他的parent，如果parent不在这些recorder里面的话。
        //如果parent在这些recorder里面，就直接组装到children里面
        fun toNodes(recorders: List<NodeRecorder>, source: Source = InternalSource): List<Pair<Node, String>> {
            val nodes = recorders.map { it.toNode(source) }
            val parentToNode: List<Pair<String, Node>> = recorders.zip(nodes).map {
                it.first.parentId to it.second
            }
            val idToNode = nodes.map { it.id to it }.toMap()
            val withNoParent = mutableListOf<Pair<Node, String>>()
            parentToNode.forEach {
                val parentId = it.first
                val node = it.second
                idToNode[parentId].let {
                    it?.children?.add(node) ?: withNoParent.add(node to parentId)
                }
            }
            return withNoParent.toList()
        }

        fun fromNodes(node: Node, parentId: String = ""): List<NodeRecorder> {
            return listOf(fromNode(node, parentId)).plus(node.children.map {
                fromNodes(it, node.id)
            }.flatten())
        }

        fun fromNode(node: Node, parentId: String) = NodeRecorder(node.id, node.title, node.markers.map { it.name }, parentId)

    }
}


fun main() {
    fun simple(id: String, ch: List<SimpleTextNode> = emptyList()) =
            SimpleTextNode(id, id, ch.toMutableList(), mutableListOf(), InternalSource)

    val path = "/Users/nielinjie/Desktop/fake.mindkit"
    val root = simple("a", listOf(
            simple("a1"),
            simple("a2", listOf(
                    simple("a21"),
                    simple("a23")
            )
            ),
            simple("a3"),
            simple("a4", listOf(
                    simple("a41"),
                    simple("a42")
            ))
    ))
    val recorders = fromNodes(root, "_root")
    FileJsonStore(File(path)).save(recorders)
}