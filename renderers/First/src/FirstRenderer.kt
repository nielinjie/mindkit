import xyz.nietongxue.mindkit.model.MindMap
import xyz.nietongxue.mindkit.view.ViewNode
import kotlin.browser.document
import kotlin.math.min

/**
 * Created by plter on 7/14/17.
 */

val electron: dynamic = js("require('electron')")

val fs:dynamic = js("require('fs')")
val path:dynamic = js("require('path')")

fun main(args: Array<String>) {
    document.body?.innerHTML = "<h1>Hello World</h1>"
    val jsonString :String = fs.readFileSync("/Users/nielinjie/Projects/practise/KotlinElectronQuickStart/mindmap.json")
    println(jsonString)
    val mindMap = MindMap.fromJson(JSON.parse(jsonString))
    ViewNode.fromNode(mindMap.sheets[0].root).pretty()
}