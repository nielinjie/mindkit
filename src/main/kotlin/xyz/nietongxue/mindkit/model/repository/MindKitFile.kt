package xyz.nietongxue.mindkit.model.repository

import xyz.nietongxue.mindkit.model.Marker
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.repository.NodeRecorder.Companion.fromNode
import xyz.nietongxue.mindkit.model.repository.NodeRecorder.Companion.toNodes
import xyz.nietongxue.mindkit.model.source.EditableSource
import xyz.nietongxue.mindkit.model.source.FileSource
import xyz.nietongxue.mindkit.model.source.Mounting
import xyz.nietongxue.mindkit.model.source.Source
import xyz.nietongxue.mindkit.util.FileJsonStore
import java.io.File

data class SimpleTextNode(override val id: String,
                          override val title: String,
                          override val children: MutableList<Node>,
                          override val markers: MutableList<Marker>,
                          @Transient
                          override val source: Source) : Node


class MindKitFileSource(override val file: File) : FileSource, EditableSource {
    override fun edit(parent: Node, node: Node) {
        val index = nodeRecorders.indexOfFirst { it.id == node.id }
        if (index == -1) throw IllegalStateException("no such node")
        nodeRecorders[index] = fromNode(node, parent.id)
        save()
    }

    override fun remove(node: Node) {
        nodeRecorders.removeIf { it.id == node.id }
        save()
    }

    override fun add(parent: Node, node: Node) {
        val index = nodeRecorders.indexOfLast { it.parentId == parent.id }
        if(index == -1) nodeRecorders.add(fromNode(node,parent.id))
        else nodeRecorders.add(index+1, fromNode(node,parent.id))
    }

    override val description: String = "MindKit文件 - ${file.name}"
    private val nodeRecorders: MutableList<NodeRecorder> = mutableListOf()

    init {
        nodeRecorders.addAll(FileJsonStore(file).load().filterIsInstance<NodeRecorder>())
    }

    override fun mount(tree: Node, mountPoint: Node): List<Mounting> {
        //NOTE mindkit文件是一条一条的node。有parent。没有child
        return toNodes(nodeRecorders, this).groupBy { it.second }.map {
            val parentId = it.key
            val nodes = it.value.map { it.first }
            tree.findById(parentId)?.let { it1 -> Mounting(it1) { nodes } }
        }.filterNotNull()
    }

    private fun save() {
        FileJsonStore(file).save(this.nodeRecorders)
    }

}
