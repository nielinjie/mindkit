package xyz.nietongxue.mindkit.model


import java.lang.IllegalStateException

//NOTE marker就是tag，是有结构有业务意义的标记。 label是自由标记。

data class Marker(val name: String, val alias: List<String>)

//NOTE 松耦合，marker首先是独立存在的。family是marker之上的一种应用。
data class MarkerFamily(val name: String, val alias: List<String>, val markers: List<String>)

object Markers {
    //TODO 需要具体应用各自定义marker 么？
    val pN: List<String> = (0..5).toList().map { "p$it" }
    val perN: List<String> = (0..100 step 10).toList().map { "${it}p" }
    val markers: List<Marker> =
            pN.map { Marker(it, emptyList()) } +
                    perN.map { Marker(it, emptyList()) } + listOf(
                    Marker("attention", emptyList()),
                    Marker("wait", emptyList()),
                    Marker("question", emptyList()),
                    Marker("task", emptyList())
            )

    val markerFamilies: List<MarkerFamily> = listOf(
            MarkerFamily("todo", listOf("task"), pN)
    )

    fun byName(name: String, byAlias: Boolean = true): Marker {
        return markers.filter { it.name == name || (byAlias && it.alias.contains(name)) }.let {
            if (it.size > 1) throw IllegalStateException("Duplicated")
            else it.first()
        }
    }
}