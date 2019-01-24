package xyz.nietongxue.mindkit.view

import tornadofx.Component
import xyz.nietongxue.mindkit.source.Source

class TreeModel : Component(){
    var root: ViewNode = ViewNode.emptyRoot

    fun mount(sources: List<Source>) {
        sources.forEach {
            //TODO mount的调用需要放到runAsync里面去。
            it.mount(root.node).forEach {
                val mounting = it
                val viewNode = root.findNode(mounting.where)
                viewNode?.addChildren(mounting.what())
            }
        }
    }
}