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
import xyz.nietongxue.mindkit.model.FileFavorite
import xyz.nietongxue.mindkit.model.FolderFavorite
import xyz.nietongxue.mindkit.util.defaultPadding
import xyz.nietongxue.mindkit.util.withAnother
import java.io.File

class FavoriteView : View() {
    override val root = VBox()
    val popoverContent = VBox()
    val allFavoriteP = SimpleListProperty<Favorite>(Favorites.all.observable())

    val favoriteP = SimpleObjectProperty<Favorite>(allFavoriteP.value.first())
    val onFavoriteSelectedP = SimpleObjectProperty<((Favorite) -> Unit)?>(null)
    var popOver: PopOver

    val folderView: FolderView = find()
    val fileView: FileView = find()



    var combo: ComboBox<Favorite> by singleAssign()

    init {
        favoriteP.withAnother(onFavoriteSelectedP).let {
            it.onChange { pair ->
                pair!!.second?.also { it(pair.first) }
            }
        }

        val currentFavoriteName = config["currentFavoriteName"] as? String
        favoriteP.value = Favorites.all.find {
            it.name() == currentFavoriteName
        } ?: Favorites.all.first()

        this.popOver = PopOver(popoverContent).apply {
            this.arrowLocation = PopOver.ArrowLocation.TOP_LEFT
            fadeOutDuration = Duration(500.0)
        }



        with (root){
            hbox {
                defaultPadding()
                hyperlink("收藏") {
                    action { popOver.show(this) }
                }
                hyperlink("打开目录") {
                    action {
                        val folder = folderView.openChooser()
                        folder?.let { addFolder(it) }
                    }
                }
                hyperlink("打开文件") {
                    action {
                        val file = fileView.openChooser()
                        file?.let { addFile(it) }
                    }
                }
            }
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

    fun addFolder(folder: File) {
        val folderFavorite = FolderFavorite(folder.absolutePath)
        val findFavorite = Favorites.all.find { it is FolderFavorite && it.path == folderFavorite.path }
        if (findFavorite == null) {
            Favorites.add(folderFavorite)
        }
        this.favoriteP.set(folderFavorite)
    }

    fun addFile(file:File){
        val fileFavorite  = FileFavorite(file.absolutePath)
        val findFavorite = Favorites.all.find { it is FolderFavorite && it.path == fileFavorite.path }
        if (findFavorite == null) {
            Favorites.add(fileFavorite)
        }
        this.favoriteP.set(fileFavorite)
    }

    fun onClose() {
        Favorites.save()
        config["currentFavoriteName"] = favoriteP.value.name()
        config.save()
    }
}
