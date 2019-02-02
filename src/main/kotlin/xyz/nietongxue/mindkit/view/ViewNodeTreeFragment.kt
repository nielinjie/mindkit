package xyz.nietongxue.mindkit.view

import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*
import xyz.nietongxue.mindkit.model.Markers
import java.awt.Font.BOLD

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
                        Markers.findFamilyByMarker(item.descendantsMarkersCache).forEach {
                            label(it.name.toUpperCase()){
                                markerStyle(true)
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

