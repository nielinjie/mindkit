package xyz.nietongxue.mindkit.model

import xyz.nietongxue.mindkit.model.Filters.symbols
import xyz.nietongxue.mindkit.util.Priority
import xyz.nietongxue.mindkit.util.scanForInstance

typealias Filter = (Node) -> Boolean

object Filters {
    fun fromString(string: List<String>): Filter {
        val filters = scanForInstance(FilterDescriptor::class).flatMap { it.fromString(string) }
        return { node: Node ->
            filters.any {
                it(node)
            }
        }
    }

    val symbols: List<String> = listOf(":")
}

interface FilterDescriptor {
    fun fromString(string: List<String>): List<Filter>
}

@Priority(1)
class TitleFilter : FilterDescriptor {
    override fun fromString(string: List<String>): List<Filter> {
        return string.filterNot { s ->
            symbols.any {
                s.startsWith(it)
            }
        }.map {
            { node: Node ->
                node.title.contains(it)
            }
        }
    }

}