package xyz.nietongxue.mindkit.view

import javafx.geometry.Pos
import javafx.scene.control.ContentDisplay
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import tornadofx.*
import xyz.nietongxue.mindkit.model.Markers
import xyz.nietongxue.mindkit.util.UIGlobal
import xyz.nietongxue.mindkit.util.ensureVisibleItem
import javafx.scene.input.KeyCode
import xyz.nietongxue.mindkit.model.source.EditableSource


class ViewNodeTreeFragment : TreeCellFragment<ViewNode>() {
    override val root: HBox = HBox()

    var textField : TextField? = null

    private fun createTextField():TextField {
        return TextField().apply {
            setOnKeyPressed { t ->
                if (t.code === KeyCode.ENTER) {
                    val text = this.text
                    item.node.title = text
                    this@ViewNodeTreeFragment.commitEdit(item)
                } else if (t.code === KeyCode.ESCAPE) {
                    this@ViewNodeTreeFragment.cancelEdit()
                }
            }
        }
    }

    override fun startEdit() {
       if (textField == null) textField = createTextField()

        this.cell!!.graphic = textField!!
        with(textField!!){
            text = item.node.title
            requestFocus()
//        setContentDisplay(ContentDisplay.GRAPHIC_ONLY)
            selectAll()
        }

        super.startEdit()

    }

    override fun commitEdit(newValue: ViewNode) {
        (newValue.node.source as EditableSource ).also{
            it.edit(newValue.parent!!,newValue.node)
        }
        setupContent()
        super.commitEdit(newValue)

    }
    override fun cancelEdit() {
        this.cell!!.graphic = root
        super.cancelEdit()

    }




    init {
        onEdit {
            startEdit()
        }
        itemProperty.onChange {
            it?.let {
                setupContent()
            }
        }

    }

    private fun setupContent() {
        with(root) {
            replaceChildren()
            alignment = Pos.CENTER_LEFT
            spacing = 10.0
            label(item.node.title)
            hbox {
                spacing = 2.0
                alignment = Pos.CENTER_LEFT
                Markers.findFamilyByMarker(item.node.markers).forEach {
                    label(it.name.toUpperCase()) {
                        markerStyle()
                    }
                }
            }
            hbox {
                spacing = 2.0
                alignment = Pos.CENTER_LEFT
                Markers.findFamilyByMarker(item.descendantsMarkersCache).forEach { mf ->
                    hyperlink(mf.name.toUpperCase()) {
                        markerStyle(true)
                        action {
                            UIGlobal.treeView?.let {
                                it.ensureVisibleItem(this@ViewNodeTreeFragment.cell!!.treeItem) { viewNode ->
                                    mf.markers.any {
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

