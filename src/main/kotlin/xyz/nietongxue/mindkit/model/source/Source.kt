package xyz.nietongxue.mindkit.model.source

import xyz.nietongxue.mindkit.model.Markers
import xyz.nietongxue.mindkit.model.Node


data class Mounting(val where: Node, private val what: () -> List<Node>){
    fun getAndMark():List<Node>{
        return what().map{
           it.collect {
               Markers.mark(it)
           }
            it
        }
    }
}
interface Source {
    val description: String
    fun mount(tree: Node, mountPoint: Node = tree): List<Mounting>
    //NOTE 设计：由source负责去寻找其他source，比如一个文件夹source，他去寻找下面的文件对应的source。也就是composite机制在此不定义，由具体的source去定义。
}

object InternalSource : Source {
    override val description: String = "内置"
    override fun mount(tree: Node, mountPoint: Node): List<Mounting> = emptyList()
}
