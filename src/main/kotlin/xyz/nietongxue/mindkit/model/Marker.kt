package xyz.nietongxue.mindkit.model


import javafx.beans.property.SimpleObjectProperty
import tornadofx.Fieldset
import tornadofx.field
import tornadofx.label
import xyz.nietongxue.mindkit.properties.PropertiesDescriptor
import xyz.nietongxue.mindkit.util.Priority

//NOTE marker就是tag，是有结构有业务意义的标记。 label是自由标记。

data class Marker(val name: String, val alias: List<String>)

//NOTE 松耦合，marker首先是独立存在的。family是marker之上的一种应用。
data class MarkerFamily(val name: String, val alias: List<String>, val markers: List<Marker>)

object Markers {
    //TODO 需要具体应用各自定义marker 么？
    private val priorityN: List<Marker> = (0..5).toList().map { "p$it" }.map { Marker(it, emptyList()) }
    private val percentN: List<Marker> = (0..100 step 10).toList().map { "${it}p" }.map { Marker(it, emptyList()) }
    private val words: List<Marker> = listOf("attention", "wait", "question", "task").map { Marker(it, emptyList()) }

    val markers: List<Marker> =
            priorityN + percentN + words

    val markerFamilies: List<MarkerFamily> = listOf(
            MarkerFamily("todo", listOf("task"), markers),
            MarkerFamily("waiting", emptyList(), listOfNotNull(byName("waiting")))
    )

    fun byName(name: String, byAlias: Boolean = true): Marker? {
        return markers.filter { it.name == name || (byAlias && it.alias.contains(name)) }.let {
            if (it.size > 1) throw IllegalStateException("Duplicated marker name")
            else it.firstOrNull()
        }
    }

    fun familyByName(name: String, byAlias: Boolean = true): MarkerFamily? {
        return markerFamilies.filter { it.name == name || (byAlias && it.alias.contains(name)) }.let {
            if (it.size > 1) throw IllegalStateException("Duplicated marker name")
            else it.firstOrNull()
        }
    }
}

@Priority(100)
class MarkerFilter : FilterDescriptor {
    override fun filter(tokens: Tokens): List<Filter> {
        //NOTE marker直接完整匹配
        val markers: List<Marker> = tokens.all.mapNotNull {
            //已经考虑了alias了。
            Markers.byName(it.name)
        } + tokens.all.mapNotNull {
            Markers.familyByName(it.name)
        }.flatMap { it.markers }

        return markers.distinctBy { it.name }.map { m: Marker ->
            { node: Node ->
                node.markers.any {
                    it.name == m.name
                }
            }
        }

    }

}


class MarkderPropertiesDescriptor : PropertiesDescriptor {
    override fun fieldSet(nodeP: SimpleObjectProperty<Node>): List<Fieldset> {
        val node = nodeP.value
        return if (node.markers.isNotEmpty()) {
            listOf(Fieldset("Marker").apply {
                field {
                    label(node.markers.joinToString(",") { it.name })
                }
            })
        } else emptyList()
    }

}