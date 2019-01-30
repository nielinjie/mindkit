package xyz.nietongxue.mindkit.model

import xyz.nietongxue.mindkit.model.source.Source

interface Node {
    val id: String
    val title: String

    val children: MutableList<Node>
    val markers: MutableList<Marker>

    val source: Source
    fun <T> collect(f: (Node) -> T): List<T> {
        return listOf(f(this)) + children.map { it.collect(f) }.flatten()
    }
}