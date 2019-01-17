package xyz.nietongxue.mindkit.model

import xyz.nietongxue.mindkit.source.Source

interface Node{
    val id: String
    val title: String
    val children: ArrayList<Node>
    val markers:List<Marker>
    //TODO 想下是否要耦合node和source
    val source: Source
}