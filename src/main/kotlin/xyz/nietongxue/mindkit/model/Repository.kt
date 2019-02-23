package xyz.nietongxue.mindkit.model

import xyz.nietongxue.mindkit.model.source.FolderSource
import xyz.nietongxue.mindkit.model.source.InternalSource
import xyz.nietongxue.mindkit.model.source.Mounting
import xyz.nietongxue.mindkit.model.source.Source
import java.io.File

interface Repository{
    fun sources(): List<Source>

    fun name(): String
}

data class FolderRepository(val base:File): Repository{
    override fun sources(): List<Source> {
        return listOf(
                FolderSource(base.absolutePath),
                RepositoryDefaultSource(base)
        )
    }

    override fun name(): String {
        return "File base Repository - ${base.name}"
    }

}
data class RepositoryDefaultSource(val base:File):Source{
    override val description: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun mount(tree: Node, mountPoint: Node): List<Mounting> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

data class SimpleTextNode(override val id: String, override val title: String, override val children: MutableList<Node>, override val markers: MutableList<Marker>) :Node {
    @Transient
    override val source: Source = InternalSource
}