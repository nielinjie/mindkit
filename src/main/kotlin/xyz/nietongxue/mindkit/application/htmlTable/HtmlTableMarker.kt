package xyz.nietongxue.mindkit.application.htmlTable

import com.beust.klaxon.lookup
import xyz.nietongxue.mindkit.application.xmind.XNode
import xyz.nietongxue.mindkit.model.Marker
import xyz.nietongxue.mindkit.model.MarkerDescriptor
import xyz.nietongxue.mindkit.model.MarkerFamily
import xyz.nietongxue.mindkit.model.Node

class HtmlTableMarker : MarkerDescriptor {
    private val ma = Marker("table", emptyList())
    override fun mark(node: Node) {
        if (when(node){
                    is XNode -> node.extensions?.lookup<String?>("provider")?.get(0) == "org.xmind.ui.spreadsheet"
                    else -> false
                }) {
            node.markers.add(ma)
        }
    }

    override val markers: List<Marker> = listOf(ma)
    override val families: List<MarkerFamily> = listOf(MarkerFamily("table", emptyList(),listOf(ma)))
}