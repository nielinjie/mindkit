package xyz.nietongxue.mindkit.view

import tornadofx.Component
import tornadofx.runLater
import xyz.nietongxue.mindkit.model.source.Source

class TreeModel : Component() {
    var root: ViewNode = ViewNode.emptyRoot()
    val rootHistory: MutableList<ViewNode> = mutableListOf(root)

    fun moveRoot(viewNode: ViewNode) {
        this.rootHistory.add(this.root)
        this.root = viewNode
    }

    fun resetRoot() {
        this.root = ViewNode.emptyRoot()
        rootHistory.clear()
        rootHistory.add(root)
    }

    fun mount(sources: List<Source>) {
        runAsync {
            sources.flatMap { it.mount(root.node) }
        } ui {

            //TODO lazyload，需要显示的时候load
            //Fixme runAsync 方式体验不错，但会有些node没有mounting的情况。出现在上级节点较多的情况。
            //可能是因为在异步模式下，有的时候子节点先出来了，父节点还没出来，所以无法mounting。

//            it.chunked(3).forEach { mountings ->
//                runAsync {
//                    println(mountings)
//                    mountings.map { it to it.getAndMark() }
//                } ui {
//                    it.forEach {
//                        val viewNode = root.findNode(it.first.where)
//                        viewNode?.addChildren(it.second)
//                    }
//                }
            it.forEach {
                root.findNode(it.where)?.addChildren(it.getAndMark())

            }

        }
    }


}