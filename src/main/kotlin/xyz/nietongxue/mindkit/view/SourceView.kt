package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleObjectProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.TreeItem
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.util.StringConverter
import tornadofx.*
import xyz.nietongxue.mindkit.model.Favorite
import xyz.nietongxue.mindkit.model.Favorites
import xyz.nietongxue.mindkit.model.Source
import java.lang.IllegalStateException

class SourceView : View() {
    override val root = VBox()
    val controller: MainController by inject()
    val treeModel = TreeModel()
    val favoriteP = SimpleObjectProperty<Favorite>(Favorites.all[0])


    init {
        with(root) {
            this += combobox(favoriteP, Favorites.all) {
                isEditable = false
                this.converter = object : StringConverter<Favorite>() {
                    override fun toString(`object`: Favorite?): String {
                        return `object`?.name!!
                    }

                    override fun fromString(string: String?): Favorite {
                        throw IllegalStateException("Can not edit")
                    }

                }
                this.onAction = EventHandler<ActionEvent> {
                    runAsync {
                        this@combobox.value.updateNode(treeModel.root.node)
                    } ui {
                        treeModel.root.removeChildren()
                        treeModel.root.addChildren(it)
                    }
                }
            }
//            this += button("load nodes") {
//                action {
//                    runAsync {
//                        Favorites.all[0].updateNode(treeModel.root.node)
//                    } ui {
//                        treeModel.root.removeChildren()
//                        treeModel.root.addChildren(it)
//                    }
//                }
//            }
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

