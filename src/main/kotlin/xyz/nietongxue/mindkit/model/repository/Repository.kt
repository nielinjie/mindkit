package xyz.nietongxue.mindkit.model.repository

import xyz.nietongxue.mindkit.model.Marker
import xyz.nietongxue.mindkit.model.Markers
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.repository.NodeRecorder.Companion.fromNodes
import xyz.nietongxue.mindkit.model.repository.NodeRecorder.Companion.toNodes
import xyz.nietongxue.mindkit.model.source.*
import xyz.nietongxue.mindkit.util.FileJsonStore
import java.io.File
import java.lang.IllegalStateException

interface Repository {
    fun sources(): List<Source>

    fun name(): String
}

data class FolderRepository(val base: File) : Repository {
    /*
        /--（root）（repository的主要形式）包括其下所有的文件，可以外链（文件、folder、其他一切）。
            --- .mindkit 文件夹
                --- nodes.json nodes
                --- (其他各种，元数据、外链等的json store)
            ---（其他文件可以存在，作为一般文件用文件source来load)
     */

    var nodesSource:MindKitFileSource?  = null
    init{
        checkAndCreate()
    }
    private fun checkAndCreate(){
        val mindkitFolder = File(base,".mindkit")
        if(mindkitFolder.isDirectory){
            nodesSource = MindKitFileSource(File(mindkitFolder,"nodes.json"))
        }else{
            if (mindkitFolder.isFile){
                throw IllegalStateException(".mindkit 文件存在，无法建立相关文件结构")
            }else{
               if( mindkitFolder.mkdir()) {
                   checkAndCreate()
                   return
               }

            }
            throw IllegalStateException("无法建立相关文件结构")
        }
    }
    override fun sources(): List<Source> {
        return listOfNotNull(
                FolderSource(base.absolutePath),
                this.nodesSource
                //outerLinkSources
        )
    }

    override fun name(): String {
        return "仓库 - ${base.name}"
    }

}

data class SimpleTextNode(override val id: String,
                          override val title: String,
                          override val children: MutableList<Node>,
                          override val markers: MutableList<Marker>,
                          @Transient
                          override val source: Source) : Node


class MindKitFileSource(override val file: File) : FileSource {
    override val description: String = "MindKit文件 - ${file.name}"
    override fun mount(tree: Node, mountPoint: Node): List<Mounting> {
        //NOTE mindkit文件是一条一条的node。有parent。没有child
        val nodeRecorders: List<NodeRecorder> = FileJsonStore(file).load().filterIsInstance<NodeRecorder>()
        return toNodes(nodeRecorders, this).groupBy { it.second }.map {
            val parentId = it.key
            val nodes = it.value.map { it.first }
            tree.findById(parentId)?.let { it1 -> Mounting(it1) { nodes } }
        }.filterNotNull()
    }


}

data class NodeRecorder(val id: String, val title: String, val markers: List<String>, val parent: String) {
    init {
        require(parent != id)
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
                it.first.parent to it.second
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
            return listOf(NodeRecorder(node.id, node.title, node.markers.map { it.name }, parentId)).plus(node.children.map {
                fromNodes(it, node.id)
            }.flatten())
        }
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
    val recorders = fromNodes(root,"_root")
    FileJsonStore(File(path)).save(recorders)
}