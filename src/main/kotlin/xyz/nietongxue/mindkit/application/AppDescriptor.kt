package xyz.nietongxue.mindkit.application

import tornadofx.View
import xyz.nietongxue.mindkit.model.Function
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.properties.PropertiesApp
import xyz.nietongxue.mindkit.util.scanForInstance


interface AppDescriptor {
    val name: String
    val description: String
    //TODO 有没有必要从tornadofx（或者其他什么view 技术）解耦合？
    val providedFunctions: List<Function>
    val appController: AppController

    companion object {
        val all: List<AppDescriptor> by lazy {

            //nonApp好像在filterNotNull的时候被滤掉了。
            //应该是因为object：AppDescriptor这种形式不会产生kotlin object，而是产生一个class
            return@lazy scanForInstance(AppDescriptor::class).sortedByDescending { it == PropertiesApp }
        }


    }
}

interface AppController {
    //TODO 到后台去运行，不能单用runAsync，因为他引起的ui的变化不在ui thread，需要在ui block。现在能做的是在实现的时候处理。
    fun process(node: Node)

    var function: Function?
    val view: View
}