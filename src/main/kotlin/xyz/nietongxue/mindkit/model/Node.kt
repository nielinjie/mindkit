package xyz.nietongxue.mindkit.model

import com.beust.klaxon.internal.firstNotNullResult
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
    fun findById(id:String):Node?{
        return if(this.id == id) this
        else{
            //TODO 不需要所有children都map一遍，第一就可以了。
            this.children.mapNotNull { it.findById(id) }.firstOrNull()
        }
    }
}