package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.layout.VBox
import javafx.util.StringConverter
import org.controlsfx.control.PopOver
import tornadofx.Component
import tornadofx.combobox
import tornadofx.observable
import xyz.nietongxue.mindkit.model.Favorite
import xyz.nietongxue.mindkit.model.Favorites
import xyz.nietongxue.mindkit.model.FolderFavorite
import xyz.nietongxue.mindkit.util.defaultPadding
import java.io.File

class FavoriteView : Component() {
    val popoverContent = VBox()
    val allFavoriteP = SimpleListProperty<Favorite>(Favorites.all.observable())

    val favoriteP = SimpleObjectProperty<Favorite>(allFavoriteP.value.first())
    var favoriteSelected: ((Favorite) -> Unit)? = null
        set(value) {
            field = value
            value?.also { it(favoriteP.value) }
        }
    var popOver: PopOver

    


    init {
        val currentFavoriteName = config["currentFavoriteName"] as? String
        favoriteP.value = Favorites.all.find {
            it.name() == currentFavoriteName
        } ?: Favorites.all.first()

        with(popoverContent) {
            defaultPadding()
            combobox(favoriteP, allFavoriteP) {
                isEditable = false
                this.converter = object : StringConverter<Favorite>() {
                    override fun toString(`object`: Favorite?): String {
                        return `object`?.name()!!
                    }

                    override fun fromString(string: String?): Favorite {
                        throw IllegalStateException("Can not edit")
                    }

                }
                this.onAction = EventHandler<ActionEvent> {
                    //NOTE 代替是favorite的行为，而不是source的，所以source是append
                    favoriteP.value = this.value
                    favoriteSelected?.also { it(this.value) }
                }
            }
        }

        this.popOver = PopOver(popoverContent).apply {
            this.arrowLocation = PopOver.ArrowLocation.TOP_LEFT
        }

    }

    //TODO 找个该在的地方
    fun addFolder(folder: File) {
        val folderFavorite = FolderFavorite(folder.absolutePath)
        val findFavorite = Favorites.all.find { it is FolderFavorite && it.path == folderFavorite.path }
        if (findFavorite == null) {
            Favorites.add(folderFavorite)
//            this.allFavoriteP.add(folderFavorite)
        }
        this.favoriteP.set(folderFavorite)
        //TODO 整理，可能应该放到onChange里面
        favoriteSelected?.also { it(this.favoriteP.value) }
    }

    fun onClose() {
        Favorites.save()
        config["currentFavoriteName"] = favoriteP.value.name()
        config.save()
    }
}