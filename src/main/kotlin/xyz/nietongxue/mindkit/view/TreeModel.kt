package xyz.nietongxue.mindkit.view

import xyz.nietongxue.mindkit.model.Source

class TreeModel {
    var root: ViewNode = ViewNode.emptyRoot

    fun mount(sources: List<Source>) {
        sources.forEach {
            with(it.mount(root.node)) {
                root.findNode(this.where)?.also {
                    it.addChildren(this.what)
                }
            }
        }
    }
}