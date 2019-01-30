package xyz.nietongxue.mindkit.application.task

import xyz.nietongxue.mindkit.application.xmind.XNode
import xyz.nietongxue.mindkit.model.*

class TaskMarker : MarkerDescriptor {


    private val priorityN: List<Marker> = (0..5).toList().map { "p$it" }.map { Marker(it, emptyList()) }
    private val percentN: List<Marker> = (0..100 step 10).toList().map { "${it}p" }.map { Marker(it, emptyList()) }
    private val words: List<Marker> = listOf("attention", "wait", "question", "task").map { Marker(it, emptyList()) }
    private val octs: List<Marker> = listOf("oct", "3oct", "5oct", "7oct").map { Marker(it, emptyList()) }

    //TODO Marker的识别应该是source/node的职责还是application的职责？
    //能不能分在两头处理？

    private val xnodeMarkerMap: Map<String, String> = mapOf(
            "c_symbol_hourglass" to "wait",
            "priority-0" to "p0",
            "priority-1" to "p1",
            "priority-2" to "p2",
            "priority-3" to "p3",
            "priority-4" to "p4",
            "priority-5" to "p5",
            "symbol-attention" to "attention",
            "symbol-question" to "question",
            "task-oct" to "task oct",
            "task-3oct" to "task 3oct",
            "task-5oct" to "task 5oct",
            "task-7oct" to "task 7oct",
            "task-done" to "task 100p",
            "task-half" to "task 50p",
            "task-start" to "task 0p"

            //TODO 增加更多的marker。注意id不等于icon的文件名
            //task-5oct 没有，oct是不是八分之的意思？
            //写个程序，把所有的markerId全部列出来。
            //来一个debug用的的action
    )

    override fun mark(node: Node) {
        val markers = when(node){
            is XNode -> node.originalMarkers.map { mapFromXNode(it) }
            else -> emptyList()
        }.flatten().distinct()
        node.markers.addAll(markers)
    }

    override val markers: List<Marker> = priorityN + percentN + words + octs
    override val families: List<MarkerFamily> = listOf(
            MarkerFamily("todo", listOf("task"), markers)
    )

    private fun mapFromXNode(originMarker:String):List<Marker>{

        return listOfNotNull(xnodeMarkerMap[originMarker]?.split(" ")?.let { names:List<String> ->
            names.mapNotNull {Markers.byName(it) }
        }).flatten()
    }
}