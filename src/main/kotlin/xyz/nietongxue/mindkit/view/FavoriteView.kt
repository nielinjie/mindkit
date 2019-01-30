package xyz.nietongxue.mindkit.view


import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.ComboBox
import javafx.scene.layout.VBox
import javafx.util.Duration
import javafx.util.StringConverter
import org.controlsfx.control.PopOver
import tornadofx.*
import xyz.nietongxue.mindkit.model.Favorite
import xyz.nietongxue.mindkit.model.Favorites
import xyz.nietongxue.mindkit.model.FolderFavorite
import xyz.nietongxue.mindkit.util.defaultPadding
import xyz.nietongxue.mindkit.util.withAnother
import java.io.File

class FavoriteView : Component() {
    val popoverContent = VBox()
    val allFavoriteP = SimpleListProperty<Favorite>(Favorites.all.observable())

    val favoriteP = SimpleObjectProperty<Favorite>(allFavoriteP.value.first())
    val onFavoriteSelectedP = SimpleObjectProperty<((Favorite) -> Unit)?>(null)
    var popOver: PopOver

    val pair = favoriteP.withAnother(onFavoriteSelectedP).let {
        it.onChange { pair ->
                pair!!.second?.also { it(pair.first) }
        }
    }

    var combo: ComboBox<Favorite> by singleAssign()

    init {


        val currentFavoriteName = config["currentFavoriteName"] as? String
        favoriteP.value = Favorites.all.find {
            it.name() == currentFavoriteName
        } ?: Favorites.all.first()

        this.popOver = PopOver(popoverContent).apply {
            this.arrowLocation = PopOver.ArrowLocation.TOP_LEFT
            fadeOutDuration = Duration(500.0)
        }

        with(popoverContent) {
            defaultPadding()
            combo = combobox(favoriteP, allFavoriteP) {
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
                    this@FavoriteView.popOver.hide()
                }
            }
        }


    }

    //TODO 找个该在的地方
    fun addFolder(folder: File) {
        val folderFavorite = FolderFavorite(folder.absolutePath)
        val findFavorite = Favorites.all.find { it is FolderFavorite && it.path == folderFavorite.path }
        if (findFavorite == null) {
            Favorites.add(folderFavorite)
        }
        this.favoriteP.set(folderFavorite)
    }

    fun onClose() {
        Favorites.save()
        config["currentFavoriteName"] = favoriteP.value.name()
        config.save()
    }
}
