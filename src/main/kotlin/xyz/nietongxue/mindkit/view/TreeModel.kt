package xyz.nietongxue.mindkit.view

import tornadofx.runAsync
import tornadofx.ui
import xyz.nietongxue.mindkit.source.Source

class TreeModel {
    var root: ViewNode = ViewNode.emptyRoot

    fun mount(sources: List<Source>) {
        sources.forEach {
            //TODO mount的调用需要放到runAsync里面去。
            it.mount(root.node).forEach {
                runAsync {
                    val mounting = it
                    val viewNode = root.findNode(mounting.where)
                    viewNode to mounting
                } ui {
                    it.first?.addChildren(it.second.what())
                }
            }
        }
    }
}