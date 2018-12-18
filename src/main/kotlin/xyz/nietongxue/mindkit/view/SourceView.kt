package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleObjectProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TreeItem
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.util.StringConverter
import org.controlsfx.control.PopOver
import tornadofx.*
import xyz.nietongxue.mindkit.model.Favorite
import xyz.nietongxue.mindkit.model.Favorites
import java.lang.IllegalStateException

class SourceView : View() {
    override val root = VBox()
    val controller: MainController by inject()
    val treeModel = TreeModel()
    val favoriteP = SimpleObjectProperty<Favorite>(Favorites.all[0])



    init {
        val popover = PopOver(Button("button"))
        with(root) {
            //            this += combobox(favoriteP, Favorites.all) {
//                isEditable = false
//                this.converter = object : StringConverter<Favorite>() {
//                    override fun toString(`object`: Favorite?): String {
//                        return `object`?.name!!
//                    }
//
//                    override fun fromString(string: String?): Favorite {
//                        throw IllegalStateException("Can not edit")
//                    }
//
//                }
//                this.onAction = EventHandler<ActionEvent> {
//                    //NOTE 代替是favorite的行为，而不是source的，所以source是append
//                    treeModel.root.removeChildren()
//                    treeModel.mount(this.value.sources())
//                }
//                treeModel.root.removeChildren()
//                treeModel.mount(this.value.sources())
//
//            }

            button("popover") {

                action {
                    popover.show(this)
                }
            }

            scrollpane {
                isFitToHeight = true
                isFitToWidth = true
                vboxConstraints {
                    this.vGrow = Priority.ALWAYS
                }
                treeview<ViewNode> {
                    root = TreeItem(treeModel.root)
                    root.isExpanded = true
                    cellFormat { text = it.node.title }
                    onUserSelect {
                        controller.selectedNode = it.node
                    }
                    populate {
                        it.value.children
                    }
                }

            }
        }

    }


}


