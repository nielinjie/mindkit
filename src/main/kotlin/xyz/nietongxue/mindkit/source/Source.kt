package xyz.nietongxue.mindkit.source

import xyz.nietongxue.mindkit.application.xmind.XMindSource
import xyz.nietongxue.mindkit.model.Node
import java.io.File

//TODO 一个目录作为home（repository）


/*TODO filter？或者合并在类型里面 filter貌似应该是app级别的，有些app关注这些节点，有些app关注哪些节点？
   filter跟processor如何配合？apply一个app将应用多个filter 和多个 function？
 */

data class Mounting(val where: Node, val what: List<Node>)
interface Source {
    val description:String
    fun mount(tree: Node, mountPoint: Node = tree): List<Mounting>
    //NOTE 设计：由source负责去寻找其他source，比如一个文件夹source，他去寻找下面的文件对应的source。也就是composite机制在此不定义，由具体的source去定义。
}

/**
@flat 如果是true，表示不需要保留文件层次结构的node。
 */
class FolderSource(val path: String, val flat: Boolean = true) : Source {
    override val description: String = "来自于文件夹 - $path"

    init {

    }

    override fun mount(tree: Node, mountPoint: Node): List<Mounting> {
        //TODO 只实现了flat是true
        assert(flat)
        return File(path).walk().filter {
            //TODO 实现根据文件内容选择不同的source
             it.isFile && it.extension == "xmind"
        }.map {
            XMindSource(it.path)
        }.toList().flatMap {
            it.mount(tree, mountPoint)
        }
    }

}

