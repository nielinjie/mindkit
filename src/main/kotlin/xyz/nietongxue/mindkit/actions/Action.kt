package xyz.nietongxue.mindkit.actions

//TODO 可以替代function？
//TODO 以tabpane组织，从properties的tab触发，每个action的结果放到一个单独的tab。可以关闭。

interface Action {
    val brief:String
    val description:String
    fun action()
}