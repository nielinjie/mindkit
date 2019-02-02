package xyz.nietongxue.mindkit.model


import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Pos
import tornadofx.*
import xyz.nietongxue.mindkit.model.properties.PropertiesDescriptor
import xyz.nietongxue.mindkit.util.Priority
import xyz.nietongxue.mindkit.util.scanForInstance
import xyz.nietongxue.mindkit.view.bigMarkerStyle

//NOTE marker就是tag，是有结构有业务意义的标记。 label是自由标记。

data class Marker(val name: String, val alias: List<String>)

//NOTE 松耦合，marker首先是独立存在的。family是marker之上的一种应用。
data class MarkerFamily(val name: String, val alias: List<String>, val markers: List<Marker>)


interface MarkerDescriptor {
    fun mark(node: Node)
    val markers: List<Marker>
    val families: List<MarkerFamily>
}


object Markers {


    private val markerDescriptors = scanForInstance(MarkerDescriptor::class)

    fun mark(node: Node) = markerDescriptors.forEach { it.mark(node) }

    fun findByFamily(node: Node, familyName: String): List<Node> {
        return node.collect { n ->
            if (familyByName(familyName)?.markers?.let {
                        it.any {
                            n.markers.contains(it)
                        }
                    } == true) n else null

        }
                .filterNotNull()
    }


    fun findFamilyByMarker(markers: List<Marker>): List<MarkerFamily> {
        return markerFamilies.filter {
            markers.any { m: Marker ->
                it.markers.contains(m)
            }
        }
    }


    val markers: List<Marker> = markerDescriptors.flatMap { it.markers }.distinct()
    val markerFamilies: List<MarkerFamily> = markerDescriptors.flatMap { it.families }.distinct()

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
                    hbox {
                        spacing = 5.0
                        alignment = Pos.CENTER_LEFT
                        node.markers.forEach {
                            label(it.name.toUpperCase()) {
                                bigMarkerStyle()
                            }
                        }
                    }
                }
            })
        } else emptyList()
    }

}