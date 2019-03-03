package xyz.nietongxue.mindkit.model.source

import xyz.nietongxue.mindkit.model.Node


interface EditableSource : Source {
    fun edit(parent :Node,node: Node)
    fun remove(node: Node)
    fun add(parent: Node,after:Node?, node: Node)
}

object MemoryTextSource: EditableSource {
    override fun edit(parent: Node, node: Node) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val description: String = "来自于内存"

    //TODO 设计：一个输入框，输入文本，整理为树形，再通过app输出，包括纯文本输出、xmind输出。 一个强大的source？
    // 打开的地方有打开folder、文件、剪贴板等等，都是一些source
    override fun mount(tree: Node, mountPoint: Node): List<Mounting> {
        throw NotImplementedError("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    override fun remove(node: Node) {
        throw NotImplementedError("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(parent: Node,after:Node?, node: Node) {
        throw NotImplementedError("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}