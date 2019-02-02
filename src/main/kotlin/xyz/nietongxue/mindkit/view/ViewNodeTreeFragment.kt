package xyz.nietongxue.mindkit.view

import javafx.geometry.Pos
import javafx.scene.layout.HBox
import tornadofx.*
import xyz.nietongxue.mindkit.model.Markers
import xyz.nietongxue.mindkit.util.UIGlobal
import xyz.nietongxue.mindkit.util.ensureVisibleItem

class ViewNodeTreeFragment : TreeCellFragment<ViewNode>() {
    override val root: HBox = HBox()

    init {
        itemProperty.onChange {
            root.replaceChildren()
            it?.let {
                with(root) {
                    alignment = Pos.CENTER_LEFT
                    spacing = 10.0
                    label(item.node.title)
                    hbox {
                        spacing = 2.0
                        alignment = Pos.CENTER_LEFT
                        Markers.findFamilyByMarker(item.node.markers).forEach {
                            label(it.name.toUpperCase()){
                                markerStyle()
                            }
                        }
                    }
                    hbox {
                        spacing = 2.0
                        alignment = Pos.CENTER_LEFT
                        Markers.findFamilyByMarker(item.descendantsMarkersCache).forEach { mf ->
                            hyperlink(mf.name.toUpperCase()){
                                markerStyle(true)
                                action{
                                    UIGlobal.treeView?.let{
                                        it.ensureVisibleItem(this@ViewNodeTreeFragment.cell!!.treeItem) { viewNode ->
                                            mf.markers.any{
                                                viewNode.node.markers.contains(it)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    opacity = when (item.searchResult) {
                        ViewNode.SearchResult.CHILD -> 0.5
                        else -> 1.0
                    }
                }
            }
        }

    }
}

