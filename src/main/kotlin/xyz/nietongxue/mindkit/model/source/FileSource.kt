package xyz.nietongxue.mindkit.model.source

import xyz.nietongxue.mindkit.model.Marker
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.util.scanForInstance
import java.io.File


/**
@flat 如果是true，表示不需要保留文件层次结构的node。
 */
class FolderSource(val path: String, val flat: Boolean = true) : Source, Openable {
    override val description: String = "文件夹 - $path"
    private val fileSourceDescriptors = scanForInstance(FileSourceDescriptor::class)

    init {

    }

    override val file = File(path)
    override fun mount(tree: Node, mountPoint: Node): List<Mounting> {
        //NOTE 只实现了flat是true，貌似没必要flat为false
        require(flat)
        return File(path).walk().map { file ->
            FileNode(file, this) to (fileSourceDescriptors.flatMap { it.fileToSource(file) })
        }.toList().filter {
            it.second.isNotEmpty()
        }.flatMap { p ->
            listOf(Mounting(mountPoint) { listOf(p.first) }) +
                    p.second.flatMap { it.mount(tree, p.first) }
        }
    }

}

interface Openable {
    val file: File
}

interface FileSource:Source{
    val file:File
}

class FileNode(val file: File, override val source: Source) : Node {
    override val id: String = file.toURI().toString()
    override val title: String = file.name
    override val children: MutableList<Node> = mutableListOf()
    override val markers: List<Marker> = listOf()
}

interface FileSourceDescriptor {
    fun fileToSource(file: File): List<FileSource>
}


