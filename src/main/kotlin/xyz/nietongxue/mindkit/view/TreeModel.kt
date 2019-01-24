package xyz.nietongxue.mindkit.view

import tornadofx.Component
import xyz.nietongxue.mindkit.source.Source

class TreeModel : Component(){
    var root: ViewNode = ViewNode.emptyRoot()
    val rootHistory:MutableList<ViewNode> = mutableListOf(root)

    fun moveRoot(viewNode: ViewNode){
        this.rootHistory.add(this.root)
        this.root = viewNode
    }
    fun resetRoot(){
        this.root = ViewNode.emptyRoot()
        rootHistory.clear()
        rootHistory.add(root)
    }
    fun mount(sources: List<Source>) {
        sources.forEach {
            //TODO mount的调用需要放到runAsync里面去。
            it.mount(root.node).forEach {
                runAsync {
                    val mounting = it
                    val viewNode = root.findNode(mounting.where)
                    viewNode to mounting
                } ui {
                    it.first?.addChildren(it.second.what)
                }
            }
        }
    }
}