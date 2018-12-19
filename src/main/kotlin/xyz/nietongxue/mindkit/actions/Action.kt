package xyz.nietongxue.mindkit.actions

import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.util.scanForInstance

//TODO 可以替代function？
//TODO 以tabpane组织，从properties的tab触发，每个action的结果放到一个单独的tab。可以关闭。


interface ActionDescriptor {
    fun actions(node: Node): List<Action>

    companion object {
        fun actions(node: Node): List<Action> {
            return scanForInstance(ActionDescriptor::class).flatMap { it.actions(node) }
        }
    }
}

interface Action {
    val brief: String
    val description: String
    //TODO 定义运行些啥
    fun action()
}

object GeneralActions : ActionDescriptor {
    override fun actions(node: Node): List<Action> {
        return listOf(object : Action {
            override val brief: String = "收"
            override val description: String
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

            override fun action() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

}