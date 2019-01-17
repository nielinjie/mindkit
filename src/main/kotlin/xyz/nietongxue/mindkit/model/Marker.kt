package xyz.nietongxue.mindkit.model

//NOTE marker就是tag，是有结构有业务意义的标记。 label是自由标记。

data class Marker(val name: String, val alias: List<String>, val markerFamily: String)


data class MarkerFamily(val name: String, val alias: List<String>)

object Markers{
    val tasks = MarkerFamily("tasks", listOf("todo"))
    val taskMarkers :List<Marker> = listOf(
            Marker("1")
    )
}