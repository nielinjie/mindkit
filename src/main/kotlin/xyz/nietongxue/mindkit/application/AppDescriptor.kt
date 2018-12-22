package xyz.nietongxue.mindkit.application

import tornadofx.View
import xyz.nietongxue.mindkit.model.Function
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.properties.PropertiesApp
import xyz.nietongxue.mindkit.util.scanForInstance



interface AppController{

    fun process(node: Node)
    var function: Function?
    val view:View
}