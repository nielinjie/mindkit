package xyz.nietongxue.mindkit.model



interface EditableSource : Source {
    fun edit(node: Node)
    fun remove(node: Node)
    fun add(parent: Node, node: Node)
}

object MemoryTextSource:EditableSource{
    //TODO 设计：一个输入框，输入文本，整理为树形，再通过app输出，包括纯文本输出、xmind输出。
    override fun mount(tree: Node, mountPoint: Node): List<Mounting> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun edit(node: Node) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(node: Node) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(parent: Node, node: Node) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}