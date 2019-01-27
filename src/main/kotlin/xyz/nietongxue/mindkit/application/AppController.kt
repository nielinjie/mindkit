package xyz.nietongxue.mindkit.application

import tornadofx.View
import xyz.nietongxue.mindkit.model.Node


//整理app概念。除了properties，其他都是action，不是app。
//NOTE app是需要根据node的选择有直接变化的单位。
//目前只有properties。

interface AppController{

    fun process(node: Node)
    val view:View
}