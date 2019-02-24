package xyz.nietongxue.mindkit.model.repository

import xyz.nietongxue.mindkit.model.Marker
import xyz.nietongxue.mindkit.model.Markers
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.source.*
import xyz.nietongxue.mindkit.util.FileJsonStore
import java.io.File

interface Repository {
    fun sources(): List<Source>

    fun name(): String
}

data class FolderRepository(val base: File) : Repository {
    /*
        /--（root）（repository的主要形式）包括其下所有的文件，可以外链（文件、folder、其他一切）。
            --- .repository (其他源信息，比如外链)（json形式的数据库，jsonStore）
            --- .mindkit 文件，全是json形式的node（这个名字怎么起了？）
            ---（其他文件可以存在，作为一般文件用文件source来load)
     */
    override fun sources(): List<Source> {
        return listOf(
                FolderSource(base.absolutePath)
                //outerLinkSources
        )
    }

    override fun name(): String {
        return "File base Repository - ${base.name}"
    }

}

data class SimpleTextNode(override val id: String,
                          override val title: String,
                          override val children: MutableList<Node>,
                          override val markers: MutableList<Marker>,
                          override val source: Source) : Node


class MindKitFileSource(path: String) : FileSource {
    override val file: File = File(path)
    override val description: String = "MindKit文件 - ${file.name}"
    override fun mount(tree: Node, mountPoint: Node): List<Mounting> {
        //NOTE mindkit文件是一条一条的node。有parent。没有child
        //TODO lazy?
        val nodeRecorders: List<NodeRecorder> = FileJsonStore(file).load().filterIsInstance<NodeRecorder>()

        toNodes(nodeRecorders).groupBy { it.second }.map {
            val parentId = it.key
            val nodes = it.value.map { it.first }

        }
    }

    //返回node和他的parent，如果parent不在这些recorder里面的话。
    //如果parent在这些recorder里面，就直接组装到children里面
    fun toNodes(recorders: List<NodeRecorder>): List<Pair<Node, String>> {
        val nodes = recorders.map{ it.toNode(this)}
        val parentToNode: Map<String, Node> = recorders.zip(nodes).map{
            it.first.parent to it.second
        }.toMap()
        val idToNode = nodes.map{it.id to it}.toMap()
        
    }
}

data class NodeRecorder(val id: String, val title: String, val markers: List<String>, val parent: String) {
    fun toNode(source: Source): Node {
        return SimpleTextNode(
                id, title, mutableListOf(), markers.mapNotNull {
            Markers.byName(it)
        }.toMutableList(), source)

    }
}


object MindKitFileSourceDescriptor : FileSourceDescriptor {
    override fun fileToSource(file: File): List<FileSource> {
        return if (file.isFile && file.extension == "mindKit")
            listOf(MindKitFileSource(file.path))
        else emptyList()
    }
}