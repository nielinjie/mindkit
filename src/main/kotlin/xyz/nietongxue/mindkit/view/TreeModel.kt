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
            it.forEach {mounting ->
                runAsync{
                    mounting.what()
                } ui {
                    val viewNode = root.findNode(mounting.where)
                    //TODO lazyload，需要显示的时候load
                    viewNode?.addChildren(it)
                }

            }
        }
    }


}