package xyz.nietongxue.mindkit.model

data class Marker(val name: String, val alias: List<String>, val markerFamily: String)


data class MarkerFamily(val name: String, val alias: List<String>)
//
//object Markers{
//    val tasks = MarkerFamily("tasks", listOf("todo"))
//    val taskMarkers :List<Marker> = listOf(
//            Marker("1")
//    )
//}