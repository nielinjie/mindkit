package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleObjectProperty
import javafx.event.ActionEvent
import javafx.event.Event
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TreeItem
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.util.Callback
import javafx.util.StringConverter
import org.controlsfx.control.HyperlinkLabel
import org.controlsfx.control.PopOver
import tornadofx.*
import xyz.nietongxue.mindkit.model.Favorite
import xyz.nietongxue.mindkit.model.Favorites
import xyz.nietongxue.mindkit.util.defaultPadding
import java.lang.IllegalStateException
import java.util.Random
import org.controlsfx.control.cell.ColorGridCell
import org.controlsfx.control.GridView
import org.controlsfx.control.GridCell


class SourceView : View() {
    override val root = VBox()
    val controller: MainController by inject()
    val treeModel = TreeModel()

    val favoriteView: FavoriteView = find()

    init {
        favoriteView.onActionF = { favorite ->
            treeModel.root.removeChildren()
            treeModel.mount(favorite.sources())
        }
        with(root) {
            defaultPadding()
            hyperlink("收藏") {
                action { favoriteView.popOver.show(this) }
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


