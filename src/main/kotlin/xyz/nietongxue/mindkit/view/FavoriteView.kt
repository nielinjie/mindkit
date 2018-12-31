package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleObjectProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.layout.VBox
import javafx.util.StringConverter
import org.controlsfx.control.PopOver
import tornadofx.Component
import tornadofx.combobox
import xyz.nietongxue.mindkit.model.Favorite
import xyz.nietongxue.mindkit.model.Favorites
import xyz.nietongxue.mindkit.util.defaultPadding

class FavoriteView:Component(){
    val favoriteP = SimpleObjectProperty<Favorite>(Favorites.all[0])
    val treeModel = find<TreeModel>()


    val popoverContent = VBox()
    val popover = PopOver(popoverContent).apply {
        this.arrowLocation = PopOver.ArrowLocation.TOP_LEFT
    }
    init {
        with(popoverContent) {
            defaultPadding()
            combobox(favoriteP, xyz.nietongxue.mindkit.model.Favorites.all) {
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
                    //NOTE 代替是favorite的行为，而不是source的，所以source是append
                    treeModel.root.removeChildren()

                    treeModel.mount(this.value.sources())
                }

                treeModel.root.removeChildren()
                treeModel.mount(this.value.sources())
            }
        }


    }
}
