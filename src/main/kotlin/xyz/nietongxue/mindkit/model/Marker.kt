package xyz.nietongxue.mindkit.model


import xyz.nietongxue.mindkit.util.Priority
import java.lang.IllegalStateException

//NOTE marker就是tag，是有结构有业务意义的标记。 label是自由标记。

data class Marker(val name: String, val alias: List<String>)

//NOTE 松耦合，marker首先是独立存在的。family是marker之上的一种应用。
data class MarkerFamily(val name: String, val alias: List<String>, val markers: List<Marker>)

object Markers {
    //TODO 需要具体应用各自定义marker 么？
    val priorityN: List<Marker> = (0..5).toList().map { "p$it" }.map { Marker(it, emptyList()) }
    val percentN: List<Marker> = (0..100 step 10).toList().map { "${it}p" }.map { Marker(it, emptyList()) }
    val words:List<Marker> = listOf("attention","wait","question", "task").map { Marker(it, emptyList()) }

    val markers: List<Marker> =
            priorityN + percentN + words

    val markerFamilies: List<MarkerFamily> = listOf(
            MarkerFamily("todo", listOf("task"), markers),
            MarkerFamily("waiting", emptyList(), listOfNotNull(byName("waiting")))
    )

    fun byName(name: String, byAlias: Boolean = true): Marker? {
        return markers.filter { it.name == name || (byAlias && it.alias.contains(name)) }.let {
            if (it.size > 1) throw IllegalStateException("Duplicated")
            else it.firstOrNull()
        }
    }
}

@Priority(100)
class MarkerFilter:FilterDescriptor{
    override fun fromString(string: List<String>): List<Filter> {
        //TODO marker要不要完整匹配？目前觉得应该
        //marker直接匹配
        val markers:List<Filter> = (string.map { Markers.byName(it) }).filterNotNull().map{
            m:Marker ->
                { node:Node ->
                    node.markers.any{
                        //TODO 考虑alias
                        it.name == m.name
                    }
                }
        }
        //markerFamily匹配
        return markers
    }

}