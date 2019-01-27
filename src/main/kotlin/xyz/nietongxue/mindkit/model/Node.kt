package xyz.nietongxue.mindkit.model

import xyz.nietongxue.mindkit.model.source.Source

interface Node{
    val id: String
    val title: String

    val children: MutableList<Node>
    val markers:List<Marker>

    val source: Source
}